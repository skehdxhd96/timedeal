package com.example.timedeal.Event.dto;

import com.example.timedeal.Event.entity.Event;
import com.example.timedeal.Event.entity.EventStatus;
import com.example.timedeal.Event.entity.PublishEvent;
import com.example.timedeal.product.entity.ProductEvents;
import com.example.timedeal.user.entity.User;

import java.util.ArrayList;

public class EventAssembler {

    private EventAssembler() {}

    public static Event event(EventSaveRequest request, User currentUser) {
        return Event.builder()
                .createdBy(currentUser)
                .eventType(request.getEventType())
                .build();
    }

    public static PublishEvent publishEvent(Event event, PublishEventSaveRequest request) {
        return PublishEvent.builder()
                .eventCode(generateEventCode(event, request)) // 202303TIMEDEAL
                .eventName(request.getEventName())
                .eventStatus(EventStatus.IN_PROGRESS)
                .productEvents(new ProductEvents())
                .eventStartTime(request.getEventStartTime())
                .eventEndTime(request.getEventEndTime())
                .eventDesc(request.getEventDesc())
                .build();
    }

    public static String generateEventCode(Event event, PublishEventSaveRequest request) {
        // year + month + eventName
        return String.format("%d-%d-s",request.getEventStartTime().getYear(),
                                        request.getEventStartTime().getMonth().getValue(),
                                        event.getEventType().toUpperCase());
    }
}
