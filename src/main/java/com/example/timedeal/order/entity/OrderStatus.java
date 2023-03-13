package com.example.timedeal.order.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {

    SUCCESS("주문성공", "SUCCESS"),
    FAILED("주문실패", "FAILED"),
    WAIT("주문대기", "WAITED");

    private final String key;
    private final String value;
}
