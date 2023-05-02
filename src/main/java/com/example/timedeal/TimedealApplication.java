package com.example.timedeal;

import com.example.timedeal.Event.entity.Event;
import com.example.timedeal.Event.entity.EventStatus;
import com.example.timedeal.Event.entity.PublishEvent;
import com.example.timedeal.Event.repository.EventRepository;
import com.example.timedeal.Event.repository.PublishEventRepository;
import com.example.timedeal.Event.service.EventService;
import com.example.timedeal.common.exception.BusinessException;
import com.example.timedeal.common.exception.ErrorCode;
import com.example.timedeal.product.controller.EventType;
import com.example.timedeal.product.entity.Product;
import com.example.timedeal.product.entity.ProductEvent;
import com.example.timedeal.product.entity.ProductEvents;
import com.example.timedeal.product.repository.ProductEventRepository;
import com.example.timedeal.product.repository.ProductRepository;
import com.example.timedeal.product.service.ProductService;
import com.example.timedeal.user.entity.Administrator;
import com.example.timedeal.user.entity.Consumer;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EnableCaching
@EnableRedisHttpSession
@EnableJpaAuditing
@SpringBootApplication
public class TimedealApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimedealApplication.class, args);
    }

}
