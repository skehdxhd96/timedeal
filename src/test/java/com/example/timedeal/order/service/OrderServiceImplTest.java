package com.example.timedeal.order.service;

import com.example.timedeal.common.factory.ProductFactory;
import com.example.timedeal.common.factory.UserFactory;
import com.example.timedeal.order.dto.OrderItemSaveRequest;
import com.example.timedeal.order.dto.OrderSaveRequest;
import com.example.timedeal.order.repository.OrderRepository;
import com.example.timedeal.product.dto.ProductSaveRequest;
import com.example.timedeal.product.entity.Product;
import com.example.timedeal.product.repository.ProductRepository;
import com.example.timedeal.product.service.ProductService;
import com.example.timedeal.stock.entity.StockHistory;
import com.example.timedeal.stock.repository.StockHistoryRepository;
import com.example.timedeal.stock.service.StockService;
import com.example.timedeal.user.entity.Administrator;
import com.example.timedeal.user.entity.Consumer;
import com.example.timedeal.user.entity.User;
import com.example.timedeal.user.entity.UserType;
import com.example.timedeal.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@RequiredArgsConstructor
class OrderServiceImplTest {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    StockHistoryRepository stockHistoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StockService stockService;

    @BeforeEach
    void setProductAndStock() {

        Administrator administrator = Administrator.builder()
                .userName("admin")
                .password("admin")
                .userType(UserType.ADMINISTRATOR)
                .build();
        Consumer consumer = Consumer.builder()
                .userName("test")
                .password("1234")
                .address("korea")
                .userType(UserType.CONSUMER)
                .build();

        userRepository.saveAndFlush(administrator);
        userRepository.saveAndFlush(consumer);

        ProductSaveRequest productSaveRequest = ProductSaveRequest.builder()
                .productName("test product 1")
                .description("test product 1 desc")
                .productPrice(10000)
                .totalStockQuantity(300)
                .build();

        productService.register(administrator, productSaveRequest);
    }

    @Test
    void 동시성_재고_테스트() throws InterruptedException {

        Consumer consumer = (Consumer) userRepository.findById(2L).get();

        OrderItemSaveRequest orderItemSaveRequest = OrderItemSaveRequest.builder()
                .itemPrice(10000)
                .quantity(1)
                .productId(1L)
                .build();

        OrderSaveRequest orderSaveRequest = new OrderSaveRequest();
        orderSaveRequest.getOrderItemRequests().add(orderItemSaveRequest);

        Product product = productRepository.findById(1L).get();

        assertThat(orderSaveRequest.getOrderItemRequests()).hasSize(1);
        assertThat(consumer.getId()).isEqualTo(2L);
        assertThat(stockService.getStockRemaining(product)).isEqualTo(300);

        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for(int i=0; i<threadCount; i++) {
            executorService.submit(() -> {
                try {
                    orderService.doOrder(orderSaveRequest ,consumer);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        List<StockHistory> all = stockHistoryRepository.findAll();

        assertThat(all).hasSize(100);
        assertThat(stockService.getStockRemaining(product)).isEqualTo(200);
    }

    @Test
    void doOrder() {
    }

    @Test
    void findMyOrderList() {
    }

    @Test
    void findOrderedList() {
    }

    @Test
    void findOrderDetail() {
    }

    @Test
    void saveHistory() {
    }

    @Test
    void createEmptyOrder() {
    }
}