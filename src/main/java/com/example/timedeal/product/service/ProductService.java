package com.example.timedeal.product.service;

import com.example.timedeal.common.entity.RestPage;
import com.example.timedeal.product.dto.*;
import com.example.timedeal.product.entity.Product;
import com.example.timedeal.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Long register(User currentUser, ProductSaveRequest request);

    void remove(Long productId);

    ProductSelectResponse update(User currentUser, Long id, ProductUpdateRequest request);

    Product findDetails(Long id);

    Page<ProductSelectResponse> findAllProducts(Pageable pageable, ProductSearchRequest searchRequest);

    RestPage<ProductEventSelectResponse> findAllProductsOnEvent(Pageable pageable, String eventName);

    void assignEvent(Long productId, ProductEventRequest request);

    void terminateEvent(Long productId, Long publishEventId);
}
