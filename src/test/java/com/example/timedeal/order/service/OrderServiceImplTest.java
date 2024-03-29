package com.example.timedeal.order.service;

import com.example.timedeal.common.factory.ProductFactory;
import com.example.timedeal.common.factory.UserFactory;
import com.example.timedeal.order.dto.OrderItemSaveRequest;
import com.example.timedeal.order.dto.OrderSaveRequest;
import com.example.timedeal.order.entity.Order;
import com.example.timedeal.order.repository.OrderRepository;
import com.example.timedeal.product.dto.ProductSaveRequest;
import com.example.timedeal.product.entity.Product;
import com.example.timedeal.product.entity.ProductStatus;
import com.example.timedeal.product.repository.ProductRepository;
import com.example.timedeal.product.service.ProductService;
import com.example.timedeal.stock.entity.StockHistory;
import com.example.timedeal.stock.repository.StockHistoryRepository;
import com.example.timedeal.stock.service.StockOperation;
import com.example.timedeal.stock.service.StockService;
import com.example.timedeal.user.entity.Administrator;
import com.example.timedeal.user.entity.Consumer;
import com.example.timedeal.user.entity.User;
import com.example.timedeal.user.entity.UserType;
import com.example.timedeal.user.repository.UserRepository;
import com.example.timedeal.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;

@Slf4j
@SpringBootTest
@RequiredArgsConstructor
class OrderServiceImplTest {

    @Autowired
    OrderServiceImpl orderServiceImpl;

    @Mock
    UserService userService;

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

    @Autowired
    StockOperation stockOperation;

    @Test
//    @Transactional
    @DisplayName("1000명_재고_동시 주문 테스트")
    void 동시_주문_테스트() throws InterruptedException {

        Administrator administrator = Administrator.builder()
                .id(1L)
                .userName("admin")
                .password("admin")
                .userType(UserType.ADMINISTRATOR)
                .build();

        Consumer consumer = Consumer.builder()
                .id(2L)
                .userName("test")
                .password("1234")
                .address("korea")
                .userType(UserType.CONSUMER)
                .build();

        userRepository.save(administrator);
        userRepository.save(consumer);

        Product product = Product.builder()
                .createdBy(administrator)
                .description("test product 1")
                .productName("test product")
                .productStatus(ProductStatus.ON)
                .productEvent(null)
                .productPrice(10000)
                .totalStockQuantity(30000)
                .build();

        productRepository.save(product);

        OrderItemSaveRequest orderItemSaveRequest = OrderItemSaveRequest.builder()
                .itemPrice(10000)
                .quantity(30)
                .productId(1L)
                .publishEventId(null)
                .build();


        OrderSaveRequest orderSaveRequest = new OrderSaveRequest();
        orderSaveRequest.getOrderItemRequests().add(orderItemSaveRequest);

        List<Product> products = new ArrayList<>();
        products.add(product);
        List<Long> ids = new ArrayList<>();
        ids.add(1L);

        stockOperation.register(product);
        given(userService.findUser(any(Long.class)))
                .willReturn(consumer);

        int numberOfThreads = 1000;
        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            service.submit(() -> {
                try {
                    orderServiceImpl.doOrder(orderSaveRequest, consumer);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        List<StockHistory> allOrderHistory = stockHistoryRepository.findAll();
        List<Order> allOrder = orderRepository.findAll();

        assertThat(allOrder).hasSize(1000);
        assertThat(allOrderHistory).hasSize(1000);
        assertThat(stockService.getStockRemaining(product)).isEqualTo(0);
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