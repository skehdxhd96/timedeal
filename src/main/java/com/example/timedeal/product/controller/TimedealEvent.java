package com.example.timedeal.product.controller;

import com.example.timedeal.product.dto.ProductEventSelectResponse;
import com.example.timedeal.product.dto.ProductSearchRequest;
import com.example.timedeal.product.dto.ProductSelectResponse;
import com.example.timedeal.product.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class TimedealEvent implements EventType{

    private static final String EVENTTYPE = "TIMEDEAL";
    private final ProductService productService;

    public TimedealEvent(ProductService productService) {
        this.productService = productService;
    }
    @Override
    public boolean isOnEvent(String eventCode) {
        return eventCode.contains(EVENTTYPE);
    }

    @Override
    public Page<ProductEventSelectResponse> find(Pageable pageable, String eventCode, ProductSearchRequest request) {
        return productService.findAllProductsOnEvent(pageable, eventCode);
    }
}
