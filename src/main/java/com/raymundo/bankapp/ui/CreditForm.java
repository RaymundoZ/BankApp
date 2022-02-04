package com.raymundo.bankapp.ui;

import com.raymundo.bankapp.dto.CreditDto;
import com.raymundo.bankapp.events.*;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class CreditForm extends FormLayout {

    public enum Mode {
        EDIT("Edit", "Editing credit"),
        CREATE("Create", "Creating credit");

        private final String buttonName;
        private final String labelName;

        Mode(String buttonName, String labelName) {
            this.buttonName = buttonName;
            this.labelName = labelName;
        }

        public String getButtonName() {
            return buttonName;
        }

        public String getLabelName() {
            return labelName;
        }

    }

    private final Binder<CreditDto> binder;
    private CreditDto currentCredit;
    private final NumberField creditLimit;
    private final NumberField interestRate;
    private final Button actionButton;
    private final Button deleteButton;
    private final Button cancelButton;
    private final H3 label;
    private Registration currentClickListener;

    public CreditForm() {
        binder = new BeanValidationBinder<>(CreditDto.class);
        creditLimit = new NumberField("Credit limit");
        interestRate = new NumberField("Interest rate");
        actionButton = new Button() {{
            addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        }};
        deleteButton = new Button("Delete") {{
            addThemeVariants(ButtonVariant.LUMO_ERROR);
        }};
        cancelButton = new Button("Cancel");
        label = new H3();

        binder.bindInstanceFields(this);
        add(label, creditLimit, interestRate);
        configContent();
    }

    private void configContent() {
        currentClickListener = actionButton.addClickListener(event -> Notification.show(""));
        deleteButton.addClickListener(event -> fireEvent(new DeleteEvent(this, currentCredit)));
        cancelButton.addClickListener(event -> fireEvent(new CancelEvent(this)));
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.add(cancelButton, deleteButton, actionButton);
        add(buttonLayout);
    }

    public void selectCredit(CreditDto credit, Mode mode) {
        if (mode != null) {
            actionButton.setText(mode.getButtonName());
            label.setText(mode.getLabelName());
            if (mode == Mode.CREATE) {
                currentClickListener.remove();
                deleteButton.setEnabled(false);
                currentClickListener = actionButton.addClickListener(event -> {
                    try {
                        binder.writeBean(currentCredit);
                        fireEvent(new CreateEvent(this, currentCredit));
                    } catch (ValidationException e) {
                        e.printStackTrace();
                        Notification.show("Fill all the fields!");
                    }
                });
            } else if (mode == Mode.EDIT) {
                currentClickListener.remove();
                deleteButton.setEnabled(true);
                currentClickListener = actionButton.addClickListener(event -> {
                    try {
                        binder.writeBean(currentCredit);
                        fireEvent(new EditEvent(this, currentCredit));
                    } catch (ValidationException e) {
                        e.printStackTrace();
                        Notification.show("Fill all the fields!");
                    }
                });
            }
        }
        currentCredit = credit;
        binder.readBean(credit);
    }

    public <T extends Event> Registration setListener(Class<T> event, ComponentEventListener<T> listener) {
        return getEventBus().addListener(event, listener);
    }

}
