package com.example.timedeal.Event.dto;

import com.example.timedeal.Event.entity.PublishEvent;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PublishEventSelectResponse {

    private Long id;
    private String eventName;
    private LocalDateTime eventStartTime;
    private LocalDateTime eventEndTime;
    private double eventDesc;

    @Builder
    public PublishEventSelectResponse(PublishEvent publishEvent) {
        this.id = publishEvent.getId();
        this.eventName = publishEvent.getEventName();
        this.eventStartTime = publishEvent.getEventStartTime();
        this.eventEndTime = publishEvent.getEventEndTime();
        this.eventDesc = publishEvent.getEventDesc();
    }
}
