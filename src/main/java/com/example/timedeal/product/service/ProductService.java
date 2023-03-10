package com.example.timedeal.product.service;

import com.example.timedeal.product.dto.ProductEventRequest;
import com.example.timedeal.product.dto.ProductSaveRequest;
import com.example.timedeal.product.dto.ProductSelectResponse;
import com.example.timedeal.product.dto.ProductUpdateRequest;
import com.example.timedeal.user.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    Long register(User currentUser, ProductSaveRequest request);

    void remove(Long id);

    ProductSelectResponse update(User currentUser, Long id, ProductUpdateRequest request);

    ProductSelectResponse findDetails(Long id);

    List<ProductSelectResponse> findAllProducts(String eventName, Pageable pageable);

    List<ProductSelectResponse> findAllProductsOnEvent(String eventName, Pageable pageable);

    void assignEvent(Long id, ProductEventRequest request);

    void terminateEvent(Long id, ProductEventRequest request);
}
