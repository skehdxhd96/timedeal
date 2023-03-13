package com.example.timedeal.stock.service;

import com.example.timedeal.stock.dto.Stock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TotalStockOperation implements StockOperation{

    private final RedisTemplate<String, String> redisTemplate;
    private static final String TotalStockKey = "stock:total:%d";

    @Override
    public String generateKey(Long productId) {
        return String.format(TotalStockKey, productId);
    }

    @Override
    public void add(Stock stock) {

        String key = generateKey(stock.getProductId());

        redisTemplate.execute(new SessionCallback<Object>() {

            @Override
            public <K, V> Object execute(RedisOperations<K, V> redisOperations) throws DataAccessException {
                redisOperations.multi();
                for(int count = 1; count <= stock.getQuantity(); count++) {
                    redisOperations.opsForSet().add(key, stock.getPurchaseCode().get(count).toString());
                }
                return redisOperations.exec();
            }
        });
    }

    @Override
    public void remove(Stock stock) {

        String key = generateKey(stock.getProductId());

        for(int count = 1; count <= stock.getQuantity(); count++) {
            redisOperations.opsForSet().remove(key, stock.getPurchaseCode().get(count).toString());
        }
    }

    @Override
    public Long getTotalUsedCount(RedisOperations<String, String> redisOperations, Stock stock) {

        String key = generateKey(stock.getProductId());

        return redisOperations.opsForSet().size(key);
    }
}
