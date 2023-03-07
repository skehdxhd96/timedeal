package com.example.timedeal.Event.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PublishEventSaveRequest {

    @NotNull
    private Long eventId;

    @NotBlank
    private String eventName;

    @NotNull
    private LocalDateTime eventStartTime;

    @NotNull
    private LocalDateTime eventEndTime;

    @NotNull
    private Integer eventDesc;

    @Builder
    public PublishEventSaveRequest(Long eventId, String eventName, LocalDateTime eventStartTime, LocalDateTime eventEndTime, Integer eventDesc) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.eventDesc = eventDesc;
    }
}
