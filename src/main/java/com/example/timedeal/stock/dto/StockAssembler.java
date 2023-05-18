package com.example.timedeal.stock.dto;

import com.example.timedeal.Event.entity.PublishEvent;
import com.example.timedeal.order.entity.OrderItem;
import com.example.timedeal.product.entity.Product;
import com.example.timedeal.stock.entity.StockHistory;
import com.example.timedeal.stock.entity.StockHistoryType;
import com.example.timedeal.user.entity.User;

public class StockAssembler {

    private StockAssembler() {};

    public static StockHistory stockHistory(OrderItem orderItem, User currentUser, StockHistoryType situation) {

        PublishEvent publishEvent = orderItem.getProduct().getProductEvent().getPublishEvent();
        double totalPrice = publishEvent == null ? orderItem.getItemPrice() : orderItem.getItemRealPrice();

        return StockHistory.builder()
                .consumerId(currentUser.getId())
                .productId(orderItem.getProduct().getId())
                .price(totalPrice)
                .type(situation)
                .orderId(orderItem.getOrder().getId())
                .quantity(orderItem.getQuantity())
                .price(totalPrice)
                .build();
    }
}
