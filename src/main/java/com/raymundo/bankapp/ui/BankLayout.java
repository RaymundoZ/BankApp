package com.raymundo.bankapp.ui;

import com.raymundo.bankapp.dto.BankDto;
import com.raymundo.bankapp.dto.ClientDto;
import com.raymundo.bankapp.dto.CreditDto;
import com.raymundo.bankapp.events.CancelEvent;
import com.raymundo.bankapp.events.CreateEvent;
import com.raymundo.bankapp.events.DeleteEvent;
import com.raymundo.bankapp.events.EditEvent;
import com.raymundo.bankapp.services.BankService;
import com.raymundo.bankapp.services.ClientService;
import com.raymundo.bankapp.services.CreditService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "banks", layout = MainLayout.class)
public class BankLayout extends VerticalLayout {

    private final BankService service;
    private final Grid<BankDto> grid;
    private final BankDialog dialog;


    @Autowired
    public BankLayout(BankService service, ClientService clientService, CreditService creditService) {
        this.service = service;
        this.grid = new Grid<>();
        this.dialog = new BankDialog(clientService, creditService);

        setSizeFull();
        configToolbar();
        configGrid();
        configDialog();

        refreshData();
    }

    private void configGrid() {
        grid.addComponentColumn(bank -> new Grid<ClientDto>() {{
                    addColumn(ClientDto::getName).setHeader("Name");
                    addColumn(ClientDto::getSurname).setHeader("Surname");
                    addColumn(ClientDto::getPatronymic).setHeader("Patronymic");
                    addColumn(ClientDto::getPhoneNumber).setHeader("Phone number");
                    addColumn(ClientDto::getEmail).setHeader("Email");
                    addColumn(ClientDto::getPassportNumber).setHeader("Passport number");
                    setItems(bank.getClients());
                    setSelectionMode(SelectionMode.NONE);
                }}
        ).setHeader("Clients");
        grid.addComponentColumn(bank -> new Grid<CreditDto>() {{
                    addColumn(CreditDto::getCreditLimit).setHeader("Credit Limit");
                    addColumn(CreditDto::getInterestRate).setHeader("Interest Rate");
                    setItems(bank.getCredits());
                    setSelectionMode(SelectionMode.NONE);
                }}
        ).setHeader("Credits");
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null)
                dialog.open(event.getValue());
        });

        grid.setSizeFull();
        add(grid);
    }

    private void configToolbar() {
        HorizontalLayout toolbar = new HorizontalLayout();

        Button createButton = new Button("Create new bank");
        createButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        createButton.addClickListener(event -> {
            grid.asSingleSelect().clear();
            dialog.open();
        });
        toolbar.add(createButton);
        add(toolbar);
    }

    private void configDialog() {
        dialog.setListener(CreateEvent.class, event -> {
            service.create((BankDto) event.getDto());
            refreshData();
            dialog.close();
            grid.asSingleSelect().clear();
            Notification.show("Bank was created!");
        });
        dialog.setListener(EditEvent.class, event -> {
            service.update((BankDto) event.getDto());
            refreshData();
            dialog.close();
            grid.asSingleSelect().clear();
            Notification.show("Bank was edited!");
        });
        dialog.setListener(DeleteEvent.class, event -> {
            service.delete(((BankDto) event.getDto()).getUuid());
            refreshData();
            dialog.close();
            grid.asSingleSelect().clear();
            Notification.show("Bank was deleted!");
        });
        dialog.setListener(CancelEvent.class, event -> {
            dialog.close();
            grid.asSingleSelect().clear();
        });
    }


    private void refreshData() {
        grid.setItems(new ListDataProvider<>(service.getAll()));
    }


}
