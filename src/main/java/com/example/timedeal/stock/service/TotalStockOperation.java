package com.example.timedeal.stock.service;

import com.example.timedeal.product.entity.Product;
import com.example.timedeal.stock.entity.StockHistory;
import com.example.timedeal.stock.repository.StockHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TotalStockOperation implements StockOperation{

    /**
     * Redis Stock = <StockKey : 남은 재고 양>
     */
    private final RedisTemplate<String, String> redisTemplate;
    private final StockHistoryRepository stockHistoryRepository;
    private static final String TotalStockKey = "stock:total:%d";

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

        String remaining = redisTemplate.opsForValue().get(key);

        if(remaining == null)
            return getStockRemainingIfNotExistInRedis(product);

        return Integer.parseInt(remaining);
    }

    // TODO : 동시성 문제가 발생 할 수 있나 검사( 두 스레드 동시 접근 시 Redis에 재고가 없어 RDB에서 조회할 때 )
    private int getStockRemainingIfNotExistInRedis(Product product) {

        String key = generateKey(product.getId());

        int usedStock = stockHistoryRepository
                        .findByProductId(product.getId())
                        .stream()
                        .mapToInt(StockHistory::getQuantity)
                        .sum();

        int remaining = product.getTotalStockQuantity() - usedStock;

        redisTemplate.opsForValue().set(key, String.valueOf(remaining));

        return remaining;
    }
}
