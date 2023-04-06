package com.example.timedeal;

import com.example.timedeal.Event.entity.Event;
import com.example.timedeal.Event.repository.EventRepository;
import com.example.timedeal.product.controller.EventType;
import com.example.timedeal.product.entity.Product;
import com.example.timedeal.product.repository.ProductRepository;
import com.example.timedeal.user.entity.Administrator;
import com.example.timedeal.user.entity.UserType;
import com.example.timedeal.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@EnableCaching
@EnableRedisHttpSession
@EnableJpaAuditing
@SpringBootApplication
public class TimedealApplication {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final ProductRepository productRepository;

    public TimedealApplication(UserRepository userRepository, EventRepository eventRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.productRepository = productRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(TimedealApplication.class, args);
    }

    @PostConstruct
    @Transactional
    public void initAdmin() {
        Administrator administrator = new Administrator(1L, "test1", "1234", UserType.ADMINISTRATOR);
        userRepository.save(administrator);

        List<Product> products = new ArrayList<>();
        products.add(Product.builder()
                .createdBy(administrator)
                .productName("testProduct1")
                .productPrice(10000)
                .totalStockQuantity(10)
                .build());
        products.add(Product.builder()
                .createdBy(administrator)
                .productName("testProduct2")
                .productPrice(20000)
                .totalStockQuantity(10)
                .build());
        products.add(Product.builder()
                .createdBy(administrator)
                .productName("testProduct3")
                .productPrice(30000)
                .totalStockQuantity(10)
                .build());
        products.add(Product.builder()
                .createdBy(administrator)
                .productName("testProduct4")
                .productPrice(40000)
                .totalStockQuantity(10)
                .build());
        products.add(Product.builder()
                .createdBy(administrator)
                .productName("testProduct5")
                .productPrice(50000)
                .totalStockQuantity(10)
                .build());
        productRepository.saveAll(products);

        Event event = Event.builder()
                            .createdBy(administrator)
                            .publishEvents(new ArrayList<>())
                            .eventType("TIMEDEAL")
                            .build();

        eventRepository.save(event);
    }
}
