package com.example.timedeal.home;

import com.example.timedeal.product.entity.Product;
import com.example.timedeal.product.repository.ProductRepository;
import com.example.timedeal.product.service.ProductService;
import com.example.timedeal.user.dto.UserSaveRequest;
import com.example.timedeal.user.entity.Consumer;
import com.example.timedeal.user.repository.UserRepository;
import com.example.timedeal.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    @GetMapping("/home")
    public String homeController() {
        return "Hello, This is Branch 2";
    }

    @GetMapping("/cpu")
    public String cpu() {
        log.info("cpu");
        long value = 0;

        for(long i = 0; i < 100000000L; i++) {
            value++;
        }

        return "ok";
    }
}
