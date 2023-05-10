package com.example.timedeal.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class OrderItemSaveRequest {

    @NotNull
    private Long productId;

    @NotNull
    private double itemPrice;

    @NotNull
    private int quantity;

    private Long publishEventId;

    @Builder
    public OrderItemSaveRequest(Long productId, double itemPrice, int quantity, Long publishEventId) {
        this.productId = productId;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
        this.publishEventId = publishEventId;
    }
}
