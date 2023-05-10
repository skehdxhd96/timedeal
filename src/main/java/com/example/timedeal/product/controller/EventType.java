package com.example.timedeal.product.controller;

import com.example.timedeal.product.dto.ProductSearchRequest;
import com.example.timedeal.product.dto.ProductSelectResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventType {

    boolean isOnEvent(String eventName);

    Page<? extends ProductSelectResponse> find(Pageable pageable, String eventName, ProductSearchRequest request);
}
