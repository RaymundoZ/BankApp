package com.raymundo.bankapp.events;

import com.vaadin.flow.component.Component;

public class CancelEvent extends Event {

    public CancelEvent(Component source) {
        super(source, null);
    }
}
