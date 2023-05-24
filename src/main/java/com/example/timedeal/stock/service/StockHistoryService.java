package com.example.timedeal.stock.service;

import com.example.timedeal.order.entity.Order;
import com.example.timedeal.order.entity.OrderStatus;
import com.example.timedeal.product.entity.Product;
import com.example.timedeal.stock.dto.StockAssembler;
import com.example.timedeal.stock.entity.StockHistory;
import com.example.timedeal.stock.entity.StockHistoryType;
import com.example.timedeal.stock.repository.StockHistoryRepository;
import com.example.timedeal.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockHistoryService {

    private final StockHistoryRepository stockHistoryRepository;

    @Transactional
    public void saveHistory(User currentUser, Order order, StockHistoryType situation) {

        log.info("*** 주문 히스토리 저장 시작 ***");

        List<StockHistory> StockHistory = order.getOrderItems().getElements()
                .stream()
                .map(o -> StockAssembler.stockHistory(o, currentUser, situation))
                .collect(Collectors.toList());

        stockHistoryRepository.saveAll(StockHistory);

        log.info("*** 주문 히스토리 저장 완료 ***");
    }

    public int getUsedStock(Product product) {
        return stockHistoryRepository
                .findByProductIdAndOrderStatus(product.getId(), OrderStatus.SUCCESS)
                .stream()
                .mapToInt(StockHistory::getQuantity)
                .sum();
    }
}
