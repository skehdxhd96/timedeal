package com.example.timedeal.product.service;

import com.example.timedeal.product.dto.ProductSaveRequest;
import com.example.timedeal.product.dto.ProductSelectResponse;
import com.example.timedeal.product.dto.ProductUpdateRequest;
import com.example.timedeal.user.entity.User;

public interface ProductService {

    Long register(User currentUser, ProductSaveRequest request);

    void remove(Long id);

    ProductSelectResponse update(User currentUser, Long id, ProductUpdateRequest request);

    ProductSelectResponse findDetails(Long id);
}
