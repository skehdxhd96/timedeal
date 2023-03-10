package com.example.timedeal.product.controller;

import com.example.timedeal.common.annotation.CurrentUser;
import com.example.timedeal.common.annotation.LoginCheck;
import com.example.timedeal.common.dto.AuthUser;
import com.example.timedeal.common.exception.BusinessException;
import com.example.timedeal.common.exception.ErrorCode;
import com.example.timedeal.product.dto.*;
import com.example.timedeal.product.service.ProductService;
import com.example.timedeal.user.entity.User;
import lombok.RequiredArgsConstructor;
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
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        productService.remove(id);
        return ResponseEntity.noContent().build();
    }

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

    // 요청방식 : ?page=pageNo
    @GetMapping
    public ResponseEntity<List<ProductSelectResponse>> getAllProducts(
            @RequestParam(required = false, defaultValue = "all") String eventName,
            @PageableDefault Pageable pageable
            ) {

        EventType eventType = eventTypes.stream()
                .filter(type -> type.isOnEvent(eventName))
                .findAny()
                .orElseThrow(() -> new BusinessException(ErrorCode.EVENT_NOT_MATCHING));

        List<ProductSelectResponse> productList = eventType.find(eventName, pageable);

        return ResponseEntity.ok(productList);
    }

    // 검색어, 가격으로 특정 상품을 검색한다.
    @GetMapping
    public void searchProduct(@ModelAttribute ProductSearchRequest request) {

    }

    // 상품에 이벤트를 등록한다.
    @LoginCheck(role = LoginCheck.Role.ADMINISTRATOR)
    @PostMapping("/event/{id}")
    public ResponseEntity<Void> assignEvent(@PathVariable Long id, @Valid @RequestBody ProductEventRequest request) {

        productService.assignEvent(id, request);

        return ResponseEntity.noContent().build();
    }

    // 상품의 이벤트를 해지한다.
    @LoginCheck(role = LoginCheck.Role.ADMINISTRATOR)
    @DeleteMapping("/event/{id}")
    public ResponseEntity<Void> terminateEvent(@PathVariable Long id, @Valid @RequestBody ProductEventRequest request) {

        productService.terminateEvent(id, request);

        return ResponseEntity.noContent().build();
    }

    // 상품의 주문 이력을 조회한다.
}
