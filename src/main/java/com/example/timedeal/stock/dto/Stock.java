package com.example.timedeal.stock.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class Stock {

    private Long productId;
    private Long consumerId;
    private List<UUID> purchaseCode = new ArrayList<>();
    private int price;
    private int quantity;

    public Stock(Long productId, Long consumerId, int price, int quantity) {
        this.productId = productId;
        this.consumerId = consumerId;
        this.price = price;
        this.quantity = quantity;

        for(int count = 1; count <= quantity; count++) {
            UUID uuid = UUID.randomUUID();
            purchaseCode.add(uuid);
        }
    }
}
