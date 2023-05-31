package com.example.timedeal.event.controller;

import com.example.timedeal.Event.dto.*;
import com.example.timedeal.Event.entity.Event;
import com.example.timedeal.Event.entity.PublishEvent;
import com.example.timedeal.Event.service.EventService;
import com.example.timedeal.common.dto.AuthUser;
import com.example.timedeal.common.factory.EventFactory;
import com.example.timedeal.common.factory.UserFactory;
import com.example.timedeal.user.entity.Administrator;
import com.example.timedeal.user.service.LoginService;
import com.example.timedeal.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.*;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import static com.example.timedeal.ApiDocumentUtils.getDocumentRequest;
import static com.example.timedeal.ApiDocumentUtils.getDocumentResponse;
import static org.assertj.core.api.InstanceOfAssertFactories.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
public class EventControllerTest {

    public static final String USER_SESSION_KEY = "USER_ID";
    @Autowired
    private MockMvc mvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @MockBean
    private UserService userService;
    @MockBean
    private LoginService loginService;
    @MockBean
    private EventService eventService;
    @MockBean
    private MockHttpSession httpSession;

    private static MockedStatic<EventAssembler> eventAssembler;

    @BeforeAll
    public static void beforeALl() {
        eventAssembler = mockStatic(EventAssembler.class);
    }

    @AfterAll
    public static void afterAll() {
        eventAssembler.close();
    }

    @AfterEach
    void clearSession() {
        httpSession.clearAttributes();
    }

    @Test
    @DisplayName("이벤트 등록 테스트")
    void event_add_test() throws Exception {

        EventSaveRequest request = EventFactory.eventSaveRequest();
        Administrator administrator = UserFactory.administrator();
        Event event = EventFactory.event();

        // given
        given(loginService.getCurrentUser())
                .willReturn(AuthUser.of(administrator));
        given(EventAssembler.event(any(EventSaveRequest.class), any(Administrator.class)))
                .willReturn(event);
        given(userService.findUser(any(Long.class)))
                .willReturn(administrator);

        // when
        ResultActions perform = mvc.perform(post("/api/v1/event")
                    .header("COOKIE", httpSession)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .accept(MediaType.APPLICATION_JSON_VALUE));


        // then
        perform.andExpect(status().isNoContent());
        verify(eventService, times(1))
                .add(any(EventSaveRequest.class), any(Administrator.class));

        // restdocs
        perform.andDo(document("add-event",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.COOKIE).description("SESSION")
                ),
                requestFields(
                        fieldWithPath("eventType").type(STRING).description("Event Type")
                )));
    }

    @Test
    @DisplayName("이벤트 목록 조회 테스트")
    void events_find_test() throws Exception {

        List<EventSelectResponse> events = EventFactory.eventList().stream()
                                                                    .map(EventSelectResponse::new)
                                                                    .collect(Collectors.toList());
        Administrator administrator = UserFactory.administrator();
        String expectedResponse = objectMapper.writeValueAsString(events);

        // given
        given(loginService.getCurrentUser())
                .willReturn(AuthUser.of(administrator));
        given(eventService.findEventList())
                .willReturn(events);

        // when, then
        ResultActions perform = mvc.perform(get("/api/v1/event/list")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));

        verify(eventService, times(1))
                .findEventList();

        // restdocs
        perform.andDo(document("find-eventList",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("content-type")
                ),
                responseFields(
                        fieldWithPath("[].eventType").type(ARRAY).description("event 목록")
                )));
    }

    @Test
    @DisplayName("이벤트 발행 테스트")
    void event_register_test() throws Exception {

        Administrator administrator = UserFactory.administrator();
        PublishEventSaveRequest publishEventSaveRequest = EventFactory.publishEventSaveRequest();
        PublishEvent publishEvent = EventFactory.publishEvent();

        // given
        given(loginService.getCurrentUser())
                .willReturn(AuthUser.of(administrator));
        given(EventAssembler.publishEvent(any(Event.class), any(PublishEventSaveRequest.class)))
                .willReturn(publishEvent);

        // when
        ResultActions perform = mvc.perform(post("/api/v1/event/publish")
                .header("COOKIE", httpSession)
                .content(objectMapper.writeValueAsString(publishEventSaveRequest))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE));

        // then
        perform.andExpect(status().isNoContent());
        verify(eventService, times(1))
                .publish(any(PublishEventSaveRequest.class));

        // restdocs
        perform.andDo(document("event-publish",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.COOKIE).description("SESSION")
                ),
                requestFields(
                        fieldWithPath("eventId").type(LONG).description("target event Id"),
                        fieldWithPath("eventName").type(STRING).description("publish event name"),
                        fieldWithPath("eventStartTime").type(LOCAL_DATE_TIME).description("publish event start time"),
                        fieldWithPath("eventEndTime").type(LOCAL_DATE_TIME).description("publish event end time"),
                        fieldWithPath("eventDesc").type(INTEGER).description("event's benefit. ex) 10 : 10% discount.")
                )));
    }

    @Test
    @DisplayName("발행 이벤트 목록 조회 테스트")
    void events_register_find_test() throws Exception {

        Administrator administrator = UserFactory.administrator();
        List<PublishEventSelectResponse> publishEvents = EventFactory.publishevents().stream()
                                                                                    .map(PublishEventSelectResponse::new)
                                                                                    .collect(Collectors.toList());
        String expectedResponse = objectMapper.writeValueAsString(publishEvents);

        // given
        given(loginService.getCurrentUser())
                .willReturn(AuthUser.of(administrator));
        given(eventService.findPublishEventList())
                .willReturn(publishEvents);

        // when, then
        ResultActions perform = mvc.perform(get("/api/v1/event/publish")
                        .header("COOKIE", httpSession)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk())
                        .andExpect(content().string(expectedResponse));

        verify(eventService, times(1))
                .findPublishEventList();

        // restdocs
        perform.andDo(document("find-publish-event-list",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.COOKIE).description("SESSION")
                ),
                responseFields(
                        fieldWithPath("[].id").type(ARRAY).description("publish event id"),
                        fieldWithPath("[].eventName").type(ARRAY).description("publish event name"),
                        fieldWithPath("[].eventStartTime").type(ARRAY).description("publish event start time"),
                        fieldWithPath("[].eventEndTime").type(ARRAY).description("publish event end time"),
                        fieldWithPath("[].eventDesc").type(ARRAY).description("event's benefit. ex) 10 : 10% discount")
                )));
    }
}
