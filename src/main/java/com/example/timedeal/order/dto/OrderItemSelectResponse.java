package com.example.timedeal.order.dto;

import com.example.timedeal.order.entity.OrderItem;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderItemSelectResponse {

    private long orderItemId;
    private long productId;
    private String productName;
    private int itemPrice;
    private int itemRealPrice;
    private int itemTotalPrice;
    private long publishEventId;
    private int quantity;

    @Builder
    private OrderItemSelectResponse(long orderItemId, long productId, String productName, int itemPrice, int itemRealPrice,
                                   int itemTotalPrice, long publishEventId, int quantity) {
        this.orderItemId = orderItemId;
        this.productId = productId;
        this.productName = productName;
        this.itemPrice = itemPrice;
        this.itemRealPrice = itemRealPrice;
        this.itemTotalPrice = itemTotalPrice;
        this.publishEventId = publishEventId;
        this.quantity = quantity;
    }

    public static OrderItemSelectResponse of(OrderItem orderItem) {
        return OrderItemSelectResponse.builder()
                .orderItemId(orderItem.getId())
                .productId(orderItem.getProduct().getId())
                .productName(orderItem.getProduct().getProductName())
                .itemPrice(orderItem.getItemPrice())
                .itemRealPrice(orderItem.getItemRealPrice())
                .itemTotalPrice(orderItem.getItemRealPrice() * orderItem.getQuantity())
                .publishEventId(orderItem.getPublishEventId())
                .quantity(orderItem.getQuantity())
                .build();
    }

}
