package com.example.timedeal.common.factory;

import com.example.timedeal.Event.dto.EventSaveRequest;
import com.example.timedeal.Event.entity.Event;

import java.util.Arrays;
import java.util.List;

public class EventFactory {

    private EventFactory() {}

    public static EventSaveRequest eventSaveRequest() {
        return new EventSaveRequest("TIMEDEAL");
    }

    public static Event event() {
        return Event.builder()
                .id(1L)
                .eventType("TIMEDEAL")
                .createdBy(UserFactory.administrator())
                .build();
    }

    public static List<Event> eventList() {
        return Arrays.asList(
                Event.builder()
                        .id(1L)
                        .eventType("TIMEDEAL")
                        .createdBy(UserFactory.administrator())
                        .build(),
                Event.builder()
                        .id(2L)
                        .eventType("HOTDEAL")
                        .createdBy(UserFactory.administrator())
                        .build()
        );
    }
}
