package com.example.timedeal.Event.dto;

import com.example.timedeal.Event.entity.Event;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EventSelectResponse {

    private String eventType;

    public EventSelectResponse(Event event) {
        this.eventType = event.getEventType();
    }
}
