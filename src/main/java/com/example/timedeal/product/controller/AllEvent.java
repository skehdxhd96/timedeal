package com.example.timedeal.product.controller;

import com.example.timedeal.product.dto.ProductSearchRequest;
import com.example.timedeal.product.dto.ProductSelectResponse;
import com.example.timedeal.product.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class AllEvent implements EventType{

    private static final String EVENTTYPE = "ALL";
    private final ProductService productService;

    public AllEvent(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public boolean isOnEvent(String eventCode) {
        return eventCode.contains(EVENTTYPE);
    }

    @Override
    public Page<ProductSelectResponse> find(Pageable pageable, String eventCode, ProductSearchRequest request) {
        return productService.findAllProducts(pageable, request);
    }
}
