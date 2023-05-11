package com.example.timedeal.stock.service;

import com.example.timedeal.product.entity.Product;

public interface StockOperation {

    String generateKey(Long productId);

    void register(Product product);
    void add(Stock stock);

    void remove(Stock stock);

    int getStockRemaining(Product product);
}
