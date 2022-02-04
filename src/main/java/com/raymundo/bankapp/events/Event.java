package com.raymundo.bankapp.events;

import com.raymundo.bankapp.dto.BaseDto;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;

public abstract class Event extends ComponentEvent<Component> {

    private final BaseDto dto;

    protected Event(Component source, BaseDto dto) {
        super(source, false);
        this.dto = dto;
    }

    public BaseDto getDto() {
        return dto;
    }
}
