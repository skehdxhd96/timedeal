package com.example.timedeal.stock.service;

import com.example.timedeal.order.entity.Order;
import com.example.timedeal.product.entity.Product;
import com.example.timedeal.product.service.ProductService;
import com.example.timedeal.stock.dto.StockAssembler;
import com.example.timedeal.stock.entity.StockHistory;
import com.example.timedeal.stock.repository.StockHistoryRepository;
import com.example.timedeal.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.timedeal.stock.entity.StockHistoryType.MINUS;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockService {
    private final StockOperation stockOperation;
    private final StockHistoryService stockHistoryService;

    /* 물건을 산다. */
    @Async
    @Transactional
    public void decreaseStockOnOrder(Order order) {

        rollBackStockOnOrder(order);

        /* 재고 감소 */
        stockOperation.decreaseAll(order);

        log.info("재고 감소 완료");
    }

    public void rollBackStockOnOrder(Order order) {

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCompletion(int status) {
                if(status == STATUS_ROLLED_BACK) {
                    stockOperation.increaseAll(order);
                }
            }
        });
    }

    // 상품의 재고를 가져온다.
    @Transactional(readOnly = true)
    public int getStockRemaining(Product product) {
        return stockOperation.getStockRemaining(product);
    }
}
