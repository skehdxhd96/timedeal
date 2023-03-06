package com.example.timedeal.product.service;

import com.example.timedeal.product.dto.ProductSaveRequest;
import com.example.timedeal.user.entity.User;

public interface ProductService {

    Long register(User currentUser, ProductSaveRequest request);
}
