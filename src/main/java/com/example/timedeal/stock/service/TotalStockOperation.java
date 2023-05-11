package com.example.timedeal.stock.service;

import com.example.timedeal.product.entity.Product;
import com.example.timedeal.stock.dto.Stock;
import com.google.common.base.Functions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class TotalStockOperation implements StockOperation{

    /**
     * Redis Stock = <StockKey : 남은 재고 양>
     */
    private final RedisTemplate<String, String> redisTemplate;
    private static final String TotalStockKey = "stock:total:%d";
    private static final String STOCK_NOT_EXIST = "-1";

    @Override
    public String generateKey(Long productId) {
        return String.format(TotalStockKey, productId);
    }

    @Override
    public void register(Product product) {
        String key = generateKey(product.getId());
        redisTemplate.opsForValue().set(key, String.valueOf(product.getTotalStockQuantity()));
    }

    @Override
    public void add(Stock stock) {

        String key = generateKey(stock.getProductId());

    }

    @Override
    public void remove(Stock stock) {

        String key = generateKey(stock.getProductId());

    }

    @Override
    public int getStockRemaining(Product product) {

        String key = generateKey(product.getId());

        Optional.ofNullable(redisTemplate.opsForValue().get(key))
                .orElse(STOCK_NOT_EXIST);

        return 1;
    }
}
