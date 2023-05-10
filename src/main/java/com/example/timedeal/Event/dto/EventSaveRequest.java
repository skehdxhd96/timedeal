package com.example.timedeal.Event.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class EventSaveRequest {
    @NotBlank
    private String eventType;

    public EventSaveRequest(String eventType) {
        this.eventType = eventType;
    }
}
