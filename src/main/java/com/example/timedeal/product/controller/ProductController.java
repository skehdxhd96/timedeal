package com.example.timedeal.product.controller;

import com.example.timedeal.common.annotation.CurrentUser;
import com.example.timedeal.common.annotation.LoginCheck;
import com.example.timedeal.common.dto.AuthUser;
import com.example.timedeal.product.dto.ProductSaveRequest;
import com.example.timedeal.product.dto.ProductSelectResponse;
import com.example.timedeal.product.dto.ProductUpdateRequest;
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

    @LoginCheck(role = LoginCheck.Role.ADMINISTRATOR)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        productService.remove(id);
        return ResponseEntity.noContent().build();
    }

//    @LoginCheck(role = LoginCheck.Role.ADMINISTRATOR)
//    @PutMapping("/{id}/event")
//    public ResponseEntity<ProductSelectResponse> updateEvent(@PathVariable Long id, @Valid @RequestBody) {
//
//    }

    @LoginCheck(role = LoginCheck.Role.ADMINISTRATOR)
    @PutMapping("/{id}")
    public ResponseEntity<ProductSelectResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductUpdateRequest request,
            @CurrentUser User currentUser
    ) {
        ProductSelectResponse productSelectResponse = productService.update(currentUser, id, request);
        String redirectUrl = String.format(REDIRECTED_URL, id);
        return ResponseEntity.created(URI.create(redirectUrl)).body(productSelectResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductSelectResponse> showDetails(@PathVariable Long id) {
        ProductSelectResponse productSelectResponse = productService.findDetails(id);
        return ResponseEntity.ok(productSelectResponse);
    }
}
