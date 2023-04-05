package com.example.timedeal.product.controller;

import com.example.timedeal.product.dto.ProductSearchRequest;
import com.example.timedeal.product.dto.ProductSelectRequest;
import com.example.timedeal.product.dto.ProductSelectResponse;
import com.example.timedeal.product.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AllEvent implements EventType{

    private static final String EVENTTYPE = "all";
    private final ProductService productService;

    public AllEvent(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public boolean isOnEvent(String eventName) {
        return EVENTTYPE.equalsIgnoreCase(eventName);
    }

    @Override
    public Page<ProductSelectResponse> find(Pageable pageable, String eventName, ProductSearchRequest request) {
        return productService.findAllProducts(pageable, request);
    }
}
