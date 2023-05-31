package com.example.timedeal.common.runner;

import com.example.timedeal.Event.service.EventService;
import com.example.timedeal.product.dto.ProductEventRequest;
import com.example.timedeal.product.dto.ProductSaveRequest;
import com.example.timedeal.product.entity.Product;
import com.example.timedeal.product.entity.ProductStatus;
import com.example.timedeal.product.repository.ProductRepository;
import com.example.timedeal.product.service.ProductService;
import com.example.timedeal.user.dto.UserSaveRequest;
import com.example.timedeal.user.entity.Administrator;
import com.example.timedeal.user.entity.Consumer;
import com.example.timedeal.user.entity.User;
import com.example.timedeal.user.repository.UserRepository;
import com.example.timedeal.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DataInitRunner implements ApplicationRunner {

    private final ProductService productService;
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

        Administrator admin = (Administrator) userRepository.findById(1L).get();

        for(int count = 2; count <= 101; count++) {
            UserSaveRequest request = UserSaveRequest.builder()
                    .username("test consumer " + count)
                    .password("1234")
                    .address("korea")
                    .build();

            userService.joinMember(request);
        }

        // 10만개 insert : 터짐
        for(int count = 1; count <= 10000; count++) {

            ProductSaveRequest request = ProductSaveRequest.builder()
                    .productName("test product " + count)
                    .productPrice(1000 * (count % 1000))
                    .description("test product " + count)
                    .totalStockQuantity(1000)
                    .build();

            productService.register(admin, request);
        }

        for(long count = 1L; count <= 10000L; count++) {
            productService.assignEvent(count, new ProductEventRequest(1L));
        }
    }
}
