package com.example.timedeal.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ProductUpdateEventRequest {

    @NotBlank
    private String dealType;

    @NotNull
    private LocalDateTime eventStartTime;

    @NotNull
    private LocalDateTime eventEndTime;

    // TODO : @NotNull GROUP
}
