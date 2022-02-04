package com.raymundo.bankapp.events;

import com.raymundo.bankapp.dto.BaseDto;
import com.vaadin.flow.component.Component;

public class DeleteEvent extends Event {

    public DeleteEvent(Component source, BaseDto dto) {
        super(source, dto);
    }
}
