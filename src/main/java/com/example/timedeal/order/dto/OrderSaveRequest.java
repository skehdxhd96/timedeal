package com.example.timedeal.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderSaveRequest {

    // 불변성

    @NotNull
    private List<OrderItemSaveRequest> orderItemRequests;

    public OrderSaveRequest() {
        this.orders = new ArrayList<>();
    }
}
