package com.example.timedeal.stock.service;

import com.example.timedeal.product.entity.Product;
import com.example.timedeal.stock.dto.Stock;
import org.springframework.data.redis.core.RedisOperations;

public interface StockOperation {

    String generateKey(Long productId);

    void register(Product product);
    void add(Stock stock);

    void remove(Stock stock);

    int getStockRemaining(Product product);
}
