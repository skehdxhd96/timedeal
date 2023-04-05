package com.example.timedeal.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class OrderSaveRequest {

    @NotNull
    private Long productId;
}
