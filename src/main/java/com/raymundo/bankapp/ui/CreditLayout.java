package com.raymundo.bankapp.ui;

import com.raymundo.bankapp.dto.CreditDto;
import com.raymundo.bankapp.events.CancelEvent;
import com.raymundo.bankapp.events.CreateEvent;
import com.raymundo.bankapp.events.DeleteEvent;
import com.raymundo.bankapp.events.EditEvent;
import com.raymundo.bankapp.services.CreditService;
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

@Route(value = "credits", layout = MainLayout.class)
public class CreditLayout extends VerticalLayout {

    private final CreditService service;
    private final Grid<CreditDto> grid;
    private final TextField filterField;
    private final CreditForm form;

    @Autowired
    public CreditLayout(CreditService service) {
        this.service = service;
        this.grid = new Grid<>();
        this.filterField = new TextField();
        form = new CreditForm();
        setSizeFull();
        configGrid();
        configToolbar();
        configForm();
        configContent();
        refreshData();
    }

    private void configGrid() {
        grid.addColumn(CreditDto::getCreditLimit).setHeader("Credit Limit");
        grid.addColumn(CreditDto::getInterestRate).setHeader("Interest Rate");
        grid.setSizeFull();
        grid.asSingleSelect().addValueChangeListener(event -> startForm(event.getValue(), CreditForm.Mode.EDIT));
    }

    private void configToolbar() {
        HorizontalLayout toolbar = new HorizontalLayout();
        filterField.setPlaceholder("Type to filter");
        filterField.setClearButtonVisible(true);
        filterField.setValueChangeMode(ValueChangeMode.LAZY);
        filterField.addValueChangeListener(event -> refreshData());
        Button createButton = new Button("Create new credit");
        createButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        createButton.addClickListener(event -> {
            grid.asSingleSelect().clear();
            startForm(new CreditDto(), CreditForm.Mode.CREATE);
        });
        toolbar.add(filterField, createButton);
        add(toolbar);
    }

    private void configForm() {
        form.setWidth(25, Unit.EM);
        form.setListener(CreateEvent.class, event -> {
            service.create((CreditDto) event.getDto());
            refreshData();
            closeForm();
            Notification.show("Credit was created!");
        });
        form.setListener(EditEvent.class, event -> {
            service.update((CreditDto) event.getDto());
            refreshData();
            closeForm();
            Notification.show("Credit was edited!");
        });
        form.setListener(DeleteEvent.class, event -> {
            service.delete(((CreditDto) event.getDto()).getUuid());
            refreshData();
            closeForm();
            Notification.show("Credit was deleted!");
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
        form.selectCredit(null, null);
        form.setVisible(false);
    }

    private void startForm(CreditDto creditDto, CreditForm.Mode mode) {
        if (creditDto == null) {
            closeForm();
            return;
        }
        form.selectCredit(creditDto, mode);
        form.setVisible(true);
    }

}
