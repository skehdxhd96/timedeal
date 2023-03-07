package com.example.timedeal.Event.service;

import com.example.timedeal.Event.dto.*;
import com.example.timedeal.Event.entity.Event;
import com.example.timedeal.Event.entity.PublishEvent;
import com.example.timedeal.Event.repository.EventRepository;
import com.example.timedeal.Event.repository.PublishEventRepository;
import com.example.timedeal.common.exception.BusinessException;
import com.example.timedeal.common.exception.ErrorCode;
import com.example.timedeal.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService{

    private final EventRepository eventRepository;
    private final PublishEventRepository publishEventRepository;

    @Override
    @Transactional
    public void add(EventSaveRequest request, User currentUser) {

        Event newEvent = EventAssembler.event(request, currentUser);

        eventRepository.save(newEvent);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventSelectResponse> findEventList() {

        return eventRepository.findAll().stream()
                .map(EventSelectResponse::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void publish(PublishEventSaveRequest request) {

        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new BusinessException(ErrorCode.EVENT_NOT_FOUND));

        PublishEvent publishEvent = EventAssembler.publishEvent(request);

        event.publish(publishEvent);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PublishEventSelectResponse> findPublishEventList() {

        return publishEventRepository.findAll().stream()
                .map(PublishEventSelectResponse::new).collect(Collectors.toList());
    }
}
