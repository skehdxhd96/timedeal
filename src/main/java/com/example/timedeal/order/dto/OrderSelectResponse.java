package com.example.timedeal.order.dto;

import com.example.timedeal.order.entity.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class OrderSelectResponse {

    private long orderId;
    private long customerId;
    private int totalPrice;
    private String orderStatus;
    private List<OrderItemSelectResponse> orderItemSelectResponseList;

    @Builder
    public OrderSelectResponse(long orderId, long customerId, int totalPrice, String orderStatus, List<OrderItemSelectResponse> orderItemSelectResponseList) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.orderItemSelectResponseList = orderItemSelectResponseList;
    }

    public static OrderSelectResponse of(Order order) {
        return OrderSelectResponse.builder()
                .customerId(order.getOrderedBy().getId())
                .orderId(order.getId())
                .totalPrice(order.getTotalPrice())
                .orderStatus(order.getOrderStatus().toString())
                .orderItemSelectResponseList(
                        Collections.unmodifiableList(order.getOrderItems().getElements()
                                .stream()
                                .map(OrderItemSelectResponse::of)
                                .collect(Collectors.toList())))
                .build();
    }
}
