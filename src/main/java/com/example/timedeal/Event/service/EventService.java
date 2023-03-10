package com.example.timedeal.Event.service;

import com.example.timedeal.Event.dto.EventSaveRequest;
import com.example.timedeal.Event.dto.EventSelectResponse;
import com.example.timedeal.Event.dto.PublishEventSaveRequest;
import com.example.timedeal.Event.dto.PublishEventSelectResponse;
import com.example.timedeal.user.entity.User;

import java.util.List;

public interface EventService {
    void add(EventSaveRequest request, User currentUser);

    List<EventSelectResponse> findEventList();

    void publish(PublishEventSaveRequest request);

    List<PublishEventSelectResponse> findPublishEventList();
}
