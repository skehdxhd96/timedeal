package com.example.timedeal.product.dto;

import com.example.timedeal.product.entity.Product;
import com.example.timedeal.user.entity.User;

public class ProductDtoAssembler {

    private ProductDtoAssembler() {}

    public static Product product(User currentUser, ProductSaveRequest request) {
        return Product.builder()
                .createdBy(currentUser)
                .description(request.getDescription())
                .productPrice(request.getProductPrice())
                .productName(request.getProductName())
                .build();
    }
}
