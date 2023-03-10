package com.example.timedeal.product.controller;

import com.example.timedeal.product.dto.ProductSelectResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventType {

    boolean isOnEvent(String eventName);

    List<ProductSelectResponse> find(String eventName, Pageable pageable);
}
