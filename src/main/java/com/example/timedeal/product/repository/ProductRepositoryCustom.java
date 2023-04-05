package com.example.timedeal.product.repository;

import com.example.timedeal.product.dto.ProductEventRequest;
import com.example.timedeal.product.dto.ProductSearchRequest;
import com.example.timedeal.product.dto.ProductSelectRequest;
import com.example.timedeal.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductRepositoryCustom {

    Page<Product> findAllProductOnEvent(Pageable pageable, String eventName);

    Page<Product> findAllProducts(Pageable pageable, ProductSearchRequest productSearchRequest);
}
