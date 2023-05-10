package com.example.timedeal.order.dto;

import com.example.timedeal.common.exception.BusinessException;
import com.example.timedeal.common.exception.ErrorCode;
import com.example.timedeal.order.entity.Order;
import com.example.timedeal.order.entity.OrderItem;
import com.example.timedeal.product.entity.Product;

import java.util.Objects;

public class OrderAssembler {
    private OrderAssembler() {}
    private static final double ORIGINAL_PRICE = 1.0;

    public static OrderItem orderItem(Product product, OrderSaveRequest orderSaveRequest, Order order) {

        OrderItemSaveRequest request = getOrderItemSaveRequest(product, orderSaveRequest);

        OrderItem orderItem = OrderItem.builder()
                .itemPrice(product.getProductPrice())
                .order(order)
                .product(product)
                .quantity(request.getQuantity())
                .publishEventId(request.getPublishEventId())
                .build();

        double eventDesc = orderItem.getPublishEventId() == null ?
                ORIGINAL_PRICE : product.getProductEvent().getPublishEvent().getEventDesc();

        orderItem.setRealPrice(eventDesc);

        return orderItem;
    }

    private static OrderItemSaveRequest getOrderItemSaveRequest(Product product, OrderSaveRequest orderSaveRequest) {

        return orderSaveRequest.getOrderItemRequests().stream()
                .filter(oi -> Objects.equals(oi.getProductId(), product.getId()))
                .findAny()
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));
    }
}
