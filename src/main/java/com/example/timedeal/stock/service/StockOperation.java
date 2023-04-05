package com.example.timedeal.stock.service;

import com.example.timedeal.stock.dto.Stock;
import org.springframework.data.redis.core.RedisOperations;

public interface StockOperation {

    String generateKey(Long productId);

    void add(Stock stock);

    void remove(Stock stock);

    Long getTotalUsedCount(RedisOperations<String, String> redisOperations, Stock stock);
}
