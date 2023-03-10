package com.example.timedeal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableCaching
@EnableRedisHttpSession
@EnableJpaAuditing
@SpringBootApplication
public class TimedealApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimedealApplication.class, args);
    }

}
