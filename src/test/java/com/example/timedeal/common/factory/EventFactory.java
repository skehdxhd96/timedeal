package com.example.timedeal.common.factory;

import com.example.timedeal.Event.dto.EventAssembler;
import com.example.timedeal.Event.dto.EventSaveRequest;
import com.example.timedeal.Event.dto.PublishEventSaveRequest;
import com.example.timedeal.Event.dto.PublishEventSelectResponse;
import com.example.timedeal.Event.entity.Event;
import com.example.timedeal.Event.entity.EventStatus;
import com.example.timedeal.Event.entity.PublishEvent;
import com.example.timedeal.product.entity.ProductEvent;
import com.example.timedeal.product.entity.ProductEvents;

import java.time.LocalDateTime;
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

    public static PublishEventSaveRequest publishEventSaveRequest() {
        return PublishEventSaveRequest.builder()
                .eventId(1L)
                .eventName("2023/05 timedeal")
                .eventStartTime(LocalDateTime.of(2023,5,1, 0, 0,0))
                .eventEndTime(LocalDateTime.of(2023,5,31,23,59,59))
                .eventDesc(10)
                .build();
    }

    public static PublishEventSelectResponse publishEventSelectResponse() {
        return PublishEventSelectResponse.builder()
                .publishEvent(publishEvent())
                .build();
    }

    public static PublishEvent publishEvent() {

        return PublishEvent.builder()
                .id(1L)
                .event(event())
                .eventStatus(EventStatus.IN_PROGRESS)
                .productEvents(new ProductEvents())
                .eventName("2023/05 timedeal")
                .eventStartTime(LocalDateTime.of(2023, 5, 1, 0, 0, 0))
                .eventEndTime(LocalDateTime.of(2023, 5, 31, 23, 59, 59))
                .eventDesc(10)
                .eventCode(EventAssembler.generateEventCode(event(), publishEventSaveRequest()))
                .build();
    }

    public static List<PublishEvent> publishevents() {
        return Arrays.asList(
                PublishEvent.builder()
                        .id(1L)
                        .event(event())
                        .eventStatus(EventStatus.IN_PROGRESS)
                        .productEvents(new ProductEvents())
                        .eventName("2023/05 timedeal")
                        .eventStartTime(LocalDateTime.of(2023,5,1, 0, 0,0))
                        .eventEndTime(LocalDateTime.of(2023,5,31,23,59,59))
                        .eventDesc(10)
                        .eventCode(EventAssembler.generateEventCode(event(), publishEventSaveRequest()))
                        .build(),
                PublishEvent.builder()
                        .id(2L)
                        .event(event())
                        .eventStatus(EventStatus.IN_PROGRESS)
                        .productEvents(new ProductEvents())
                        .eventName("2023/06 timedeal")
                        .eventStartTime(LocalDateTime.of(2023,6,1, 0, 0,0))
                        .eventEndTime(LocalDateTime.of(2023,6,30,23,59,59))
                        .eventDesc(20)
                        .eventCode(EventAssembler.generateEventCode(event(), publishEventSaveRequest()))
                        .build()
        );
    }
}
