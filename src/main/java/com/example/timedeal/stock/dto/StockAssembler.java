package com.example.timedeal.stock.dto;

import com.example.timedeal.Event.entity.PublishEvent;
import com.example.timedeal.product.entity.Product;
import com.example.timedeal.stock.entity.StockHistory;
import com.example.timedeal.user.entity.User;

public class StockAssembler {

    private StockAssembler() {};

    public static StockHistory stockHistory(Product product, User currentUser) {

        PublishEvent publishEvent = product.getProductEvent().getPublishEvent();
        double totalPrice = publishEvent == null ? product.getProductPrice()
                : product.getProductPrice() * ((double)publishEvent.getEventDesc() / (double)100);


        return StockHistory.builder()
                .consumerId(currentUser.getId())
                .price(totalPrice)
//                .purchaseCode()
                .build();
    }
}
