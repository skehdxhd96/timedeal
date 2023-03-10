package com.example.timedeal.product.repository;

import com.example.timedeal.product.dto.ProductEventRequest;
import com.example.timedeal.product.entity.Product;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductRepositoryCustom {

    List<Product> findAllProductOnEvent(String eventType, LocalDateTime now);
}
