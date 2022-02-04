package com.raymundo.bankapp.ui;

import com.raymundo.bankapp.dto.ClientDto;
import com.raymundo.bankapp.dto.CreditDto;
import com.raymundo.bankapp.dto.LoanOfferDto;
import com.raymundo.bankapp.events.*;
import com.raymundo.bankapp.services.ClientService;
import com.raymundo.bankapp.services.CreditService;
import com.raymundo.bankapp.utils.Calculator;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.shared.Registration;

import java.util.stream.Collectors;

public class LoanOfferDialog extends Dialog {

    private final ClientService clientService;
    private final CreditService creditService;
    private final Select<ClientDto> clients;
    private final Select<CreditDto> credits;
    private final NumberField creditSum;
    private final Button cancelButton;
    private final Button deleteButton;
    private final Button actionButton;
    private final H3 label;
    private final FormLayout content;
    private LoanOfferDto currentOffer;
    private Registration currentListener;

    public LoanOfferDialog(ClientService clientService, CreditService creditService) {
        this.clientService = clientService;
        this.creditService = creditService;
        this.clients = new Select<>();
        this.credits = new Select<>();
        this.creditSum = new NumberField("Credit sum");
        this.cancelButton = new Button("Cancel");
        this.deleteButton = new Button("Delete") {{
            addThemeVariants(ButtonVariant.LUMO_ERROR);
        }};
        this.actionButton = new Button() {{
            addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        }};
        this.label = new H3();
        this.content = new FormLayout();
        creditSum.setEnabled(false);

        configSelects();
        configContent();

        setCloseOnOutsideClick(false);
        add(label, content);
    }

    private void configSelects() {
        clients.setRenderer(new ComponentRenderer<>(client -> new HorizontalLayout(
                new Label(client.getName()),
                new Label(client.getSurname()),
                new Label(client.getPatronymic())
        )));
        credits.setRenderer(new ComponentRenderer<>(credit -> new HorizontalLayout(
                new Label("limit: " + credit.getCreditLimit()),
                new Label("rate: " + credit.getInterestRate())
        )));
        clients.setItems(clientService.getAll());
        clients.setLabel("Client");
        credits.setItems(creditService.getAll());
        credits.setLabel("Credit");
        credits.addValueChangeListener(event -> {
            if (event.getValue() != null) {
                creditSum.setMin(1);
                creditSum.setMax(event.getValue().getCreditLimit());
                creditSum.setHelperText(String.format("1<=value<=%.1f", event.getValue().getCreditLimit()));
                creditSum.setEnabled(true);
            }
        });
    }

    private void configContent() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        currentListener = actionButton.addClickListener(event -> Notification.show(""));
        cancelButton.addClickListener(event -> fireEvent(new CancelEvent(this)));
        deleteButton.addClickListener(event -> fireEvent(new DeleteEvent(this,
                new LoanOfferDto(currentOffer.getUuid(), clients.getValue(),
                        credits.getValue(), creditSum.getValue(), new Calculator(credits.getValue(),
                        creditSum.getValue()).getSchedules()))));

        buttonLayout.add(cancelButton, deleteButton, actionButton);
        content.add(clients, credits, creditSum, buttonLayout);
    }

    public void open() {
        currentOffer = null;
        actionButton.setText("Create");
        deleteButton.setEnabled(false);
        label.setText("Creating a loan");
        currentListener.remove();
        currentListener = actionButton.addClickListener(event -> {
            if (!creditSum.isEmpty() && creditSum.getValue() > 0 && creditSum.getValue() <= credits.getValue().getCreditLimit())
                fireEvent(new CreateEvent(this, new LoanOfferDto(clients.getValue(),
                        credits.getValue(), creditSum.getValue(),
                        new Calculator(credits.getValue(), creditSum.getValue()).getSchedules())));
            else
                Notification.show("Select correct credit sum!");
        });
        super.open();
    }

    public void open(LoanOfferDto loanOffer) {
        currentOffer = loanOffer;
        actionButton.setText("Edit");
        deleteButton.setEnabled(true);
        label.setText("Editing a loan");
        for (ClientDto client : clients.getListDataView().getItems().collect(Collectors.toList())) {
            if (client.getUuid().equals(currentOffer.getClient().getUuid())) {
                clients.setValue(client);
                break;
            }
        }
        for (CreditDto credit : credits.getListDataView().getItems().collect(Collectors.toList())) {
            if (credit.getUuid().equals(currentOffer.getCredit().getUuid())) {
                credits.setValue(credit);
                break;
            }
        }
        creditSum.setValue(currentOffer.getCreditAmount());
        currentListener.remove();
        currentListener = actionButton.addClickListener(event -> {
            if (creditSum.getValue() > 0 && creditSum.getValue() <= credits.getValue().getCreditLimit())
                fireEvent(new EditEvent(this, new LoanOfferDto(currentOffer.getUuid(),
                        clients.getValue(), credits.getValue(), creditSum.getValue(),
                        new Calculator(credits.getValue(), creditSum.getValue()).getSchedules())));
            else
                Notification.show("Select correct credit sum!");
        });
        super.open();
    }

    @Override
    public void close() {
        clients.clear();
        credits.clear();
        creditSum.setEnabled(false);
        creditSum.setHelperText(null);
        creditSum.setValue(null);
        creditSum.setInvalid(false);
        super.close();
    }

    public <T extends Event> Registration setListener(Class<T> event,
                                                      ComponentEventListener<T> listener) {
        return getEventBus().addListener(event, listener);
    }

}
