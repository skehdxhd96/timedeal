package com.example.timedeal.common.runner;

import com.example.timedeal.Event.entity.Event;
import com.example.timedeal.Event.entity.EventStatus;
import com.example.timedeal.Event.entity.PublishEvent;
import com.example.timedeal.Event.repository.EventRepository;
import com.example.timedeal.Event.repository.PublishEventRepository;
import com.example.timedeal.product.entity.Product;
import com.example.timedeal.product.entity.ProductEvent;
import com.example.timedeal.product.entity.ProductEvents;
import com.example.timedeal.product.repository.ProductEventRepository;
import com.example.timedeal.product.repository.ProductRepository;
import com.example.timedeal.user.entity.Administrator;
import com.example.timedeal.user.entity.Consumer;
import com.example.timedeal.user.entity.UserType;
import com.example.timedeal.user.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Profile("dev")
@Component
public class DataInitRunner implements ApplicationRunner {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final ProductRepository productRepository;
    private final PublishEventRepository publishEventRepository;
    private final ProductEventRepository productEventRepository;

    public DataInitRunner(UserRepository userRepository, EventRepository eventRepository, ProductRepository productRepository, PublishEventRepository publishEventRepository, ProductEventRepository productEventRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.productRepository = productRepository;
        this.publishEventRepository = publishEventRepository;
        this.productEventRepository = productEventRepository;
    }

    @PostConstruct
    @Transactional
    public void initAdmin() {
        Administrator administrator = new Administrator(1L, "test1", "1234", UserType.ADMINISTRATOR);
        userRepository.save(administrator);

        List<Product> products = new ArrayList<>();

        for(int i=1; i<=100; i++) {
            products.add(Product.builder()
                    .createdBy(administrator)
                    .productName("testProduct" + i)
                    .productPrice(i * 10000)
                    .totalStockQuantity(i * 10)
                    .build());
        }
        productRepository.saveAll(products);

        Event event = Event.builder()
                .createdBy(administrator)
                .publishEvents(new ArrayList<>())
                .eventType("TIMEDEAL")
                .build();

        eventRepository.save(event);

        PublishEvent publishEvent = PublishEvent.builder()
                .eventStatus(EventStatus.IN_PROGRESS)
                .eventDesc(10)
                .event(event)
                .productEvents(new ProductEvents())
                .eventStartTime(LocalDateTime.of(2023, 4,1,0, 0))
                .eventEndTime(LocalDateTime.of(2023, 5, 1, 0, 0))
                .eventName("2023년 4월 타임딜")
                .eventCode(String.format("%d-%d-%s",2023, 4, event.getEventType().toUpperCase()))
                .build();

        event.getPublishEvents().add(publishEvent);
        publishEventRepository.save(publishEvent);

        for(int i=0; i<100; i++) {
            Product product = products.get(i);
            ProductEvent productEvent = new ProductEvent(product, publishEvent);
            publishEvent.getProductEvents().add(productEvent);
            productEventRepository.save(productEvent);
        }

        Consumer consumer = new Consumer(2L, "test2", "1234", UserType.CONSUMER, "korea");
        userRepository.save(consumer);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Hello, Timedeal");
    }
}
