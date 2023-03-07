package com.example.timedeal.Event.dto;

import com.example.timedeal.Event.entity.PublishEvent;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PublishEventSelectResponse {

    private String eventName;
    private LocalDateTime eventStartTime;
    private LocalDateTime eventEndTime;
    private int eventDesc;

    @Builder
    public PublishEventSelectResponse(PublishEvent publishEvent) {
        this.eventName = publishEvent.getEventName();
        this.eventStartTime = publishEvent.getEventStartTime();
        this.eventEndTime = publishEvent.getEventEndTime();
        this.eventDesc = publishEvent.getEventDesc();
    }
}
