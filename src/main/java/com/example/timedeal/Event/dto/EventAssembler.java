package com.example.timedeal.Event.dto;

import com.example.timedeal.Event.entity.Event;
import com.example.timedeal.Event.entity.PublishEvent;
import com.example.timedeal.user.entity.User;

public class EventAssembler {

    private EventAssembler() {}

    public static Event event(EventSaveRequest request, User currentUser) {
        return Event.builder()
                .createdBy(currentUser)
                .eventType(request.getEventType())
                .build();
    }

    public static PublishEvent publishEvent(PublishEventSaveRequest request) {
        return PublishEvent.builder()
                .eventName(request.getEventName())
                .eventStartTime(request.getEventStartTime())
                .eventEndTime(request.getEventEndTime())
                .eventDesc(request.getEventDesc())
                .build();
    }
}
