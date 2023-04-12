package com.example.timedeal.product.controller;

import com.example.timedeal.common.annotation.CurrentUser;
import com.example.timedeal.common.annotation.LoginCheck;
import com.example.timedeal.common.exception.BusinessException;
import com.example.timedeal.common.exception.ErrorCode;
import com.example.timedeal.product.dto.*;
import com.example.timedeal.product.entity.Product;
import com.example.timedeal.product.service.ProductService;
import com.example.timedeal.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;
    private final List<EventType> eventTypes;
    private static final String REDIRECTED_URL = "/api/v1/product/%d";

    @LoginCheck(role = LoginCheck.Role.ADMINISTRATOR)
    @PostMapping
    public ResponseEntity<Void> register(@Valid @RequestBody ProductSaveRequest request, @CurrentUser User currentUser) {

        Long productId = productService.register(currentUser, request);
        String redirectUrl = String.format(REDIRECTED_URL, productId);

        return ResponseEntity.created(URI.create(redirectUrl)).build();
    }

    @LoginCheck(role = LoginCheck.Role.ADMINISTRATOR)
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> delete(@PathVariable Long productId) {

        productService.remove(productId);
        return ResponseEntity.noContent().build();
    }

    @LoginCheck(role = LoginCheck.Role.ADMINISTRATOR)
    @PutMapping("/{productId}")
    public ResponseEntity<ProductSelectResponse> update(
            @PathVariable Long productId,
            @Valid @RequestBody ProductUpdateRequest request,
            @CurrentUser User currentUser
    ) {
        ProductSelectResponse productSelectResponse = productService.update(currentUser, productId, request);
        String redirectUrl = String.format(REDIRECTED_URL, productId);
        return ResponseEntity.created(URI.create(redirectUrl)).body(productSelectResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductSelectResponse> showDetails(@PathVariable Long id) {
        Product product = productService.findDetails(id);

        return ResponseEntity.ok(ProductSelectResponse.of(product));
    }

    @GetMapping
    public ResponseEntity<Page<? extends ProductSelectResponse>> getAllProducts(
            @ModelAttribute ProductSearchRequest request,
            @RequestParam(required = false, defaultValue = "ALL") String eventCode,
            @PageableDefault Pageable pageable
    ) {

        EventType eventType = eventTypes.stream()
                .filter(type -> type.isOnEvent(eventCode))
                .findAny()
                .orElseThrow(() -> new BusinessException(ErrorCode.EVENT_NOT_MATCHING));

        Page<? extends ProductSelectResponse> productList = eventType.find(pageable, eventCode, request);

        return ResponseEntity.ok(productList);
    }

    // 상품에 이벤트를 등록한다.
    @LoginCheck(role = LoginCheck.Role.ADMINISTRATOR)
    @PostMapping("/event/{productId}")
    public ResponseEntity<Void> assignEvent(@PathVariable Long productId, @Valid @RequestBody ProductEventRequest request) {

        productService.assignEvent(productId, request);

        return ResponseEntity.noContent().build();
    }

    // 상품의 이벤트를 해지한다.
    @LoginCheck(role = LoginCheck.Role.ADMINISTRATOR)
    @PostMapping("/event/{productId}/{publishEventId}")
    public ResponseEntity<Void> terminateEvent(@PathVariable Long productId, @PathVariable Long publishEventId) {

        productService.terminateEvent(productId, publishEventId);

        return ResponseEntity.noContent().build();
    }

    // 상품의 주문 이력을 조회한다. 재고 이후.
}
