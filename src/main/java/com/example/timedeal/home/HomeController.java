package com.example.timedeal.home;

import com.example.timedeal.product.entity.Product;
import com.example.timedeal.product.repository.ProductRepository;
import com.example.timedeal.product.service.ProductService;
import com.example.timedeal.user.dto.UserSaveRequest;
import com.example.timedeal.user.entity.Consumer;
import com.example.timedeal.user.repository.UserRepository;
import com.example.timedeal.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

/*    @PostConstruct
    public void initData() {

        // user
        for(long i=1; i<=3; i++) {
            userRepository.save(Consumer.builder()
                    .id(i)
                    .userName("test" + i)
                    .password("1234")
                    .address("korea")
                    .build());
        }

        // product
        for(long i=1; i<25; i++) {
            productRepository.save(Product.builder()
                    .id(i)
                    .productName("product" + i)
                    .productPrice(1000 * i)
                    .description("desc")
                    .)
        }
    }*/

    @GetMapping("/home")
    public String homeController() {

        return "Hello, This is Branch 2";
    }
}
