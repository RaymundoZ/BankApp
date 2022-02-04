package com.raymundo.bankapp.events;

import com.raymundo.bankapp.dto.BaseDto;
import com.vaadin.flow.component.Component;

public class EditEvent extends Event {

    public EditEvent(Component source, BaseDto dto) {
        super(source, dto);
    }
}
