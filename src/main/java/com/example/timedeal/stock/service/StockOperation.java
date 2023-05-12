package com.example.timedeal.stock.service;

import com.example.timedeal.product.entity.Product;

public interface StockOperation {

    String generateKey(Long productId);

    void register(Product product);
    void add(Product product);

    void remove(Product product);

    int getStockRemaining(Product product);
}
