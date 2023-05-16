package com.example.timedeal.stock.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StockHistoryType {
    PLUS("재고 증가"),
    MINUS("재고 감소");

    private final String situation;
}
