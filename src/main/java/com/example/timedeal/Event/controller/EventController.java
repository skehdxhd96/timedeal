package com.example.timedeal.Event.controller;

import com.example.timedeal.Event.dto.EventSaveRequest;
import com.example.timedeal.Event.dto.EventSelectResponse;
import com.example.timedeal.Event.dto.PublishEventSaveRequest;
import com.example.timedeal.Event.dto.PublishEventSelectResponse;
import com.example.timedeal.Event.service.EventService;
import com.example.timedeal.common.annotation.CurrentUser;
import com.example.timedeal.common.annotation.LoginCheck;
import com.example.timedeal.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    // 이벤트 등록 ( 수정 / 삭제 구현 X )
    @LoginCheck(role = LoginCheck.Role.ADMINISTRATOR)
    @PostMapping
    public ResponseEntity<Void> add(@Valid @RequestBody EventSaveRequest request, @CurrentUser User currentUser) {
        eventService.add(request, currentUser);
        return ResponseEntity.noContent().build();
    }

    // 이벤트 조회 ( 발행가능한 이벤트 목록 조회 )
    @LoginCheck(role = LoginCheck.Role.ADMINISTRATOR)
    @GetMapping("/list")
    public ResponseEntity<List<EventSelectResponse>> findEventList() {
        List<EventSelectResponse> eventList = eventService.findEventList();
        return ResponseEntity.ok(eventList);
    }

    // 이벤트 발행 ( 수정 / 삭제 구현 x )
    @LoginCheck(role = LoginCheck.Role.ADMINISTRATOR)
    @PostMapping("/publish")
    public ResponseEntity<Void> publish(@Valid @RequestBody PublishEventSaveRequest request) {
        eventService.publish(request);
        return ResponseEntity.noContent().build();
    }

    // 발행한 이벤트 조회 ( 상품등록 시 적용가능한 이벤트 조회 )
    @LoginCheck(role = LoginCheck.Role.ADMINISTRATOR)
    @GetMapping("/publish")
    public ResponseEntity<List<PublishEventSelectResponse>> findPublishEventList() {
        List<PublishEventSelectResponse> publishEventList = eventService.findPublishEventList();
        return ResponseEntity.ok(publishEventList);
    }
}
