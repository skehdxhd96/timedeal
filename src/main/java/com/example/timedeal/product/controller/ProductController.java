package com.example.timedeal.product.controller;

import com.example.timedeal.common.annotation.CurrentUser;
import com.example.timedeal.common.annotation.LoginCheck;
import com.example.timedeal.common.dto.AuthUser;
import com.example.timedeal.product.dto.ProductSaveRequest;
import com.example.timedeal.product.service.ProductService;
import com.example.timedeal.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;
    private static final String REDIRECTED_URL = "/api/v1/product/%d";

    @LoginCheck(role = LoginCheck.Role.ADMINISTRATOR)
    @PostMapping
    public ResponseEntity<Void> register(@Valid @RequestBody ProductSaveRequest request, @CurrentUser User currentUser) {

        Long productId = productService.register(currentUser, request);
        String redirectUrl = String.format(REDIRECTED_URL, productId);

        return ResponseEntity.created(URI.create(redirectUrl)).build();
    }
}
