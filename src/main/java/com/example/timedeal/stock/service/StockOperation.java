package com.example.timedeal.stock.service;

import com.example.timedeal.order.entity.Order;
import com.example.timedeal.order.entity.OrderItem;
import com.example.timedeal.product.entity.Product;

public interface StockOperation {

    String generateKey(Long productId);

    void register(Product product);
    void increase(OrderItem orderItem);
    void increaseAll(Order order);
    void decrease(OrderItem orderItem);
    void decreaseAll(Order order);
    int getStockRemaining(Product product);
}
