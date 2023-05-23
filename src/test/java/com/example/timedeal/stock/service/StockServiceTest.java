package com.example.timedeal.stock.service;

import com.example.timedeal.order.entity.Order;
import com.example.timedeal.order.entity.OrderItem;
import com.example.timedeal.order.entity.OrderStatus;
import com.example.timedeal.order.repository.OrderRepository;
import com.example.timedeal.product.dto.ProductSaveRequest;
import com.example.timedeal.product.entity.Product;
import com.example.timedeal.product.entity.ProductStatus;
import com.example.timedeal.product.repository.ProductRepository;
import com.example.timedeal.product.service.ProductService;
import com.example.timedeal.user.entity.Administrator;
import com.example.timedeal.user.entity.Consumer;
import com.example.timedeal.user.entity.User;
import com.example.timedeal.user.entity.UserType;
import com.example.timedeal.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@RequiredArgsConstructor
class StockServiceTest {

    @Autowired
    StockService stockService;

    @Autowired
    StockOperation stockOperation;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    StockOperation stockoperation;

    @BeforeEach
    @Transactional
    void setOrder() {
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
    }

    @Test
    @Transactional
    @DisplayName("동시 재고 감소 테스트")
    void 재고_동시_테스트_100개() throws InterruptedException {

        Consumer consumer = (Consumer) userRepository.findById(2L).get();
        Administrator administrator = (Administrator) userRepository.findById(1L).get();

        Order order = Order.builder()
                .id(1L)
                .orderedBy(consumer)
                .orderStatus(OrderStatus.WAIT)
                .totalPrice(10000)
                .build();

        Product product = Product.builder()
                .id(1L)
                .productPrice(10000)
                .productEvent(null)
                .totalStockQuantity(100)
                .productStatus(ProductStatus.ON)
                .productName("test product")
                .description("test product 1")
                .createdBy(administrator)
                .build();

        order.getOrderItems().add(OrderItem.builder()
                                            .id(1L)
                                            .order(order)
                                            .publishEventId(null)
                                            .itemRealPrice(10000)
                                            .itemPrice(10000)
                                            .quantity(1)
                                            .product(product)
                                            .build());

        stockoperation.register(product);

        assertThat(product.getTotalStockQuantity()).isEqualTo(100);
        assertThat(product.getId()).isEqualTo(1L);

        int numberOfThreads = 101;
        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            service.submit(() -> {
                try {
                    stockService.decreaseStockOnOrder(order);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        assertThat(stockService.getStockRemaining(product)).isEqualTo(0);
    }
}