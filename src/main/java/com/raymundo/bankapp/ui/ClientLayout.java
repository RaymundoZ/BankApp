package com.raymundo.bankapp.ui;

import com.raymundo.bankapp.dto.ClientDto;
import com.raymundo.bankapp.events.CancelEvent;
import com.raymundo.bankapp.events.CreateEvent;
import com.raymundo.bankapp.events.DeleteEvent;
import com.raymundo.bankapp.events.EditEvent;
import com.raymundo.bankapp.services.ClientService;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "clients", layout = MainLayout.class)
public class ClientLayout extends VerticalLayout {

    private final ClientService service;
    private final Grid<ClientDto> grid;
    private final TextField filterField;
    private final ClientForm form;

    @Autowired
    public ClientLayout(ClientService service) {
        this.service = service;
        this.grid = new Grid<>();
        this.filterField = new TextField();
        this.form = new ClientForm();
        setSizeFull();
        configGrid();
        configToolbar();
        configForm();
        configContent();
        refreshData();
    }

    private void configGrid() {
        grid.addColumn(ClientDto::getName).setHeader("Name");
        grid.addColumn(ClientDto::getSurname).setHeader("Surname");
        grid.addColumn(ClientDto::getPatronymic).setHeader("Patronymic");
        grid.addColumn(ClientDto::getPhoneNumber).setHeader("Phone number");
        grid.addColumn(ClientDto::getEmail).setHeader("Email");
        grid.addColumn(ClientDto::getPassportNumber).setHeader("Passport number");
        grid.setSizeFull();
        grid.asSingleSelect().addValueChangeListener(event -> startForm(event.getValue(), ClientForm.Mode.EDIT));
    }

    private void configToolbar() {
        HorizontalLayout toolbar = new HorizontalLayout();
        filterField.setPlaceholder("Type to filter");
        filterField.setClearButtonVisible(true);
        filterField.setValueChangeMode(ValueChangeMode.LAZY);
        filterField.addValueChangeListener(event -> refreshData());
        Button createButton = new Button("Create new client");
        createButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        createButton.addClickListener(event -> {
            grid.asSingleSelect().clear();
            startForm(new ClientDto(), ClientForm.Mode.CREATE);
        });
        toolbar.add(filterField, createButton);
        add(toolbar);
    }

    private void configForm() {
        form.setWidth(25, Unit.EM);
        form.setListener(CreateEvent.class, event -> {
            service.create((ClientDto) event.getDto());
            refreshData();
            closeForm();
            Notification.show("Client was created!");
        });
        form.setListener(EditEvent.class, event -> {
            service.update((ClientDto) event.getDto());
            refreshData();
            closeForm();
            Notification.show("Client was edited!");
        });
        form.setListener(DeleteEvent.class, event -> {
            service.delete(((ClientDto) event.getDto()).getUuid());
            refreshData();
            closeForm();
            Notification.show("Client was deleted!");
        });
        form.setListener(CancelEvent.class, event -> {
            closeForm();
            grid.asSingleSelect().clear();
        });
        closeForm();
    }

    private void configContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setSizeFull();
        content.setFlexGrow(1, form);
        content.setFlexGrow(2, grid);
        add(content);
    }

    private void refreshData() {
        grid.setItems(service.getByText(filterField.getValue()));
    }

    private void closeForm() {
        form.selectClient(null, null);
        form.setVisible(false);
    }

    private void startForm(ClientDto clientDto, ClientForm.Mode mode) {
        if (clientDto == null) {
            closeForm();
            return;
        }
        form.selectClient(clientDto, mode);
        form.setVisible(true);
    }

}