package com.raymundo.bankapp.ui;

import com.raymundo.bankapp.dto.BankDto;
import com.raymundo.bankapp.dto.ClientDto;
import com.raymundo.bankapp.dto.CreditDto;
import com.raymundo.bankapp.events.*;
import com.raymundo.bankapp.services.ClientService;
import com.raymundo.bankapp.services.CreditService;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.shared.Registration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BankDialog extends Dialog {

    private final MultiSelectListBox<ClientDto> clients;
    private final MultiSelectListBox<CreditDto> credits;
    private final Button actionButton;
    private final Button deleteButton;
    private final Button cancelButton;
    private final ClientService clientService;
    private final CreditService creditService;
    private final VerticalLayout container;
    private Registration currentClickListener;
    private BankDto currentBank;

    public BankDialog(ClientService clientService, CreditService creditService) {
        this.clients = new MultiSelectListBox<>();
        this.credits = new MultiSelectListBox<>();
        this.actionButton = new Button() {{
            addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        }};
        this.deleteButton = new Button("Delete") {{
            addThemeVariants(ButtonVariant.LUMO_ERROR);
        }};
        this.cancelButton = new Button("Cancel");
        this.clientService = clientService;
        this.creditService = creditService;
        this.container = new VerticalLayout();

        configBoxes();
        configContent();

        setCloseOnOutsideClick(false);
        add(container);
    }

    private void configBoxes() {
        clients.setHeight(30, Unit.EM);
        credits.setHeight(30, Unit.EM);
        clients.setRenderer(new ComponentRenderer<>(client -> {
            HorizontalLayout component = new HorizontalLayout();
            component.add(new Label(client.getName()),
                    new Label(client.getSurname()),
                    new Label(client.getPatronymic()));
            return component;
        }));
        credits.setRenderer(new ComponentRenderer<>(credit -> {
            HorizontalLayout component = new HorizontalLayout();
            component.add(new Label("limit: " + credit.getCreditLimit()),
                    new Label("rate: " + credit.getInterestRate()));
            return component;
        }));
        clients.setItems(clientService.getAll());
        credits.setItems(creditService.getAll());
    }

    private void configContent() {
        HorizontalLayout content = new HorizontalLayout();
        content.add(clients, credits);
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.add(cancelButton, deleteButton, actionButton);
        container.add(new H3("Select elements"), content, buttonLayout);
        deleteButton.addClickListener(event -> fireEvent(new DeleteEvent(this, currentBank)));
        cancelButton.addClickListener(event -> fireEvent(new CancelEvent(this)));
        currentClickListener = actionButton.addClickListener(event -> Notification.show(""));
    }

    public void open() {
        clients.deselectAll();
        credits.deselectAll();
        this.currentBank = null;
        actionButton.setText("Create");
        deleteButton.setEnabled(false);
        currentClickListener.remove();
        currentClickListener = actionButton.addClickListener(event -> {
            List<ClientDto> clientDtos = new ArrayList<>(clients.getSelectedItems());
            List<CreditDto> creditDtos = new ArrayList<>(credits.getSelectedItems());

            if (!clientDtos.isEmpty() && !creditDtos.isEmpty())
                fireEvent(new CreateEvent(this, new BankDto(clientDtos, creditDtos)));
            else
                Notification.show("Choose at least one element in each tables!");
        });
        super.open();
    }

    public void open(BankDto currentBank) {
        clients.deselectAll();
        credits.deselectAll();
        this.currentBank = currentBank;
        actionButton.setText("Edit");
        deleteButton.setEnabled(true);
        for (ClientDto clientDto : currentBank.getClients()) {
            for (ClientDto client : clients.getListDataView().getItems().collect(Collectors.toList())) {
                if (clientDto.getUuid().equals(client.getUuid())) {
                    clients.select(client);
                    break;
                }
            }
        }
        for (CreditDto creditDto : currentBank.getCredits()) {
            for (CreditDto credit : credits.getListDataView().getItems().collect(Collectors.toList())) {
                if (creditDto.getUuid().equals(credit.getUuid())) {
                    credits.select(credit);
                    break;
                }
            }
        }
        currentClickListener.remove();
        currentClickListener = actionButton.addClickListener(event -> {
            List<ClientDto> clientDtoList = new ArrayList<>(clients.getSelectedItems());
            List<CreditDto> creditDtoList = new ArrayList<>(credits.getSelectedItems());

            if (!clientDtoList.isEmpty() && !creditDtoList.isEmpty())
                fireEvent(new EditEvent(this, new BankDto(currentBank.getUuid(),
                        clientDtoList, creditDtoList)));
            else
                Notification.show("Choose at least one element in each tables!");
        });
        super.open();
    }

    public <T extends Event> Registration setListener(Class<T> event,
                                                      ComponentEventListener<T> listener) {
        return getEventBus().addListener(event, listener);
    }

}
