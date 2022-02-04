package com.raymundo.bankapp.ui;

import com.raymundo.bankapp.dto.ClientDto;
import com.raymundo.bankapp.events.*;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class ClientForm extends FormLayout {

    public enum Mode {
        EDIT("Edit", "Editing client"),
        CREATE("Create", "Creating client");

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

    private final Binder<ClientDto> binder;
    private ClientDto currentClient;
    private final TextField name;
    private final TextField surname;
    private final TextField patronymic;
    private final TextField phoneNumber;
    private final EmailField email;
    private final TextField passportNumber;
    private final Button actionButton;
    private final Button deleteButton;
    private final Button cancelButton;
    private final H3 label;
    private Registration currentClickListener;

    public ClientForm() {
        binder = new BeanValidationBinder<>(ClientDto.class);
        name = new TextField("Name");
        surname = new TextField("Surname");
        patronymic = new TextField("Patronymic");
        phoneNumber = new TextField("Phone number");
        email = new EmailField("Email");
        passportNumber = new TextField("Passport number");
        actionButton = new Button() {{
            addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        }};
        deleteButton = new Button("Delete") {{
            addThemeVariants(ButtonVariant.LUMO_ERROR);
        }};
        cancelButton = new Button("Cancel");
        label = new H3();

        binder.bindInstanceFields(this);
        add(label, name, surname, patronymic, phoneNumber, email, passportNumber);

        configContent();
    }

    private void configContent() {
        currentClickListener = actionButton.addClickListener(event -> Notification.show(""));
        deleteButton.addClickListener(event -> fireEvent(new DeleteEvent(this, currentClient)));
        cancelButton.addClickListener(event -> fireEvent(new CancelEvent(this)));
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.add(cancelButton, deleteButton, actionButton);
        add(buttonLayout);
    }

    public void selectClient(ClientDto client, Mode mode) {
        if (mode != null) {
            actionButton.setText(mode.getButtonName());
            label.setText(mode.getLabelName());
            if (mode == Mode.CREATE) {
                currentClickListener.remove();
                deleteButton.setEnabled(false);
                currentClickListener = actionButton.addClickListener(event -> {
                    try {
                        binder.writeBean(currentClient);
                        fireEvent(new CreateEvent(this, currentClient));
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
                        binder.writeBean(currentClient);
                        fireEvent(new EditEvent(this, currentClient));
                    } catch (ValidationException e) {
                        e.printStackTrace();
                        Notification.show("Fill all the fields!");
                    }
                });
            }
        }
        currentClient = client;
        binder.readBean(client);
    }

    public <T extends Event> Registration setListener(Class<T> event, ComponentEventListener<T> listener) {
        return getEventBus().addListener(event, listener);
    }

}
