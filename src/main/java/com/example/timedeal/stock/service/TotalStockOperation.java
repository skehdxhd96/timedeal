package com.example.timedeal.stock.service;

import com.example.timedeal.common.annotation.DistributedLock;
import com.example.timedeal.common.exception.StockException;
import com.example.timedeal.order.entity.Order;
import com.example.timedeal.order.entity.OrderItem;
import com.example.timedeal.product.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class TotalStockOperation implements StockOperation{

    /**
     * Redis Stock = <StockKey : 남은 재고 양>
     */
    private final RedisTemplate<String, String> redisTemplate;
    private final StockHistoryService stockHistoryService;
    private static final String TotalStockKey = "stock:total:%d";
    private static final int SOLD_OUT = 0;

    @Override
    public String generateKey(Long productId) {
        return String.format(TotalStockKey, productId);
    }

    @Override
    public void register(Product product) {

        String key = generateKey(product.getId());
        redisTemplate.opsForValue().setIfAbsent(key, String.valueOf(product.getTotalStockQuantity()));

        log.info("Redis에 상품 {} 재고 데이터 적재. 현재 재고 : {}", product.getId(), redisTemplate.opsForValue().get(key));
    }

    @Override
    public void increase(OrderItem orderItem) {

        Product product = orderItem.getProduct();

        String key = generateKey(product.getId());

        int stockRemaining = getStockRemaining(product) + orderItem.getQuantity();

        redisTemplate.opsForValue().set(key, String.valueOf(stockRemaining));
    }

    @Override
    @DistributedLock(lockName = "stock_lock", waitTime = 3000, leaseTime = 3000, unit = TimeUnit.MILLISECONDS)
    public void increaseAll(Order order) {
        log.info("order : {}", order);

        for (OrderItem orderitem : order.getOrderItems().getElements()) log.info("해당 주문의 orderItem : {}", orderitem);

        order.getOrderItems()
                .getElements()
                .forEach(this::increase);
    }

    @Override
    @Transactional(readOnly = true)
    public void decrease(OrderItem orderItem) {

        log.info("{}의 {} 재고 감소 시작. 현재 재고 : {}", Thread.currentThread(), orderItem.getProduct().getId(), redisTemplate.opsForValue().get(generateKey(orderItem.getProduct().getId())));

        Product product = orderItem.getProduct();

        String key = generateKey(product.getId());

        int stockRemaining = getStockRemaining(product) - orderItem.getQuantity();

        if(stockRemaining == SOLD_OUT) {product.soldOut();}

        log.info("현재 스레드 : {}, 상품번호 : {} , 현재 재고 : {}", Thread.currentThread(), product.getId(), stockRemaining);

        redisTemplate.opsForValue().set(key, String.valueOf(stockRemaining));

        log.info("{}의 {} 재고 감소 끝", Thread.currentThread(), orderItem.getProduct().getId());
    }

    @Override
    @Transactional
    @DistributedLock(lockName = "stock_lock", waitTime = 3000, leaseTime = 3000, unit = TimeUnit.MILLISECONDS)
    public void decreaseAll(Order order) {
        try {
            order.getOrderItems()
                    .getElements().stream()
                    .peek(o -> o.validatedOnStock(getStockRemaining(o.getProduct())))
                    .forEach(this::decrease);

            order.success();

            log.info("재고 감소 성공");
        } catch(StockException e) {
            // TODO: order.failed까지 되는지 확인해야함.
            log.info("재고 감소 실패");
            log.error(e.getMessage());
            order.failed();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public int getStockRemaining(Product product) {

        String key = generateKey(product.getId());

        String remaining = redisTemplate.opsForValue().get(key);

        log.info("{} 상품 Redis 조회 시작. 현재 재고 : {}", product, remaining);

        if(remaining == null)
            return getStockRemainingIfNotExistInRedis(product);

        return Integer.parseInt(remaining);
    }

    // TODO : 동시성 문제가 발생 할 수 있나 검사( 두 스레드 동시 접근 시 Redis에 재고가 없어 RDB에서 조회할 때 )
    @Transactional(readOnly = true)
    public int getStockRemainingIfNotExistInRedis(Product product) {

        String key = generateKey(product.getId());

        log.info("재고 Redis에 없음. RDB 조회 시작. Redis 결과 : {}" , redisTemplate.opsForValue().get(key));

        int usedStock = stockHistoryService.getUsedStock(product);

        int remaining = product.getTotalStockQuantity() - usedStock;

        redisTemplate.opsForValue().set(key, String.valueOf(remaining));

        return remaining;
    }
}
