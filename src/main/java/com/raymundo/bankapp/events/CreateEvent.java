package com.raymundo.bankapp.events;

import com.raymundo.bankapp.dto.BaseDto;
import com.vaadin.flow.component.Component;

public class CreateEvent extends Event {

    public CreateEvent(Component source, BaseDto dto) {
        super(source, dto);
    }

}
