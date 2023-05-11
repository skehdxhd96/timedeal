package com.example.timedeal.product.dto;

import com.example.timedeal.product.entity.Product;
import com.example.timedeal.user.entity.User;

public class ProductAssembler {

    private ProductAssembler() {}

    public static Product product(User currentUser, ProductSaveRequest request) {
        return Product.builder()
                .createdBy(currentUser)
                .description(request.getDescription())
                .productPrice(request.getProductPrice())
                .productName(request.getProductName())
                .totalStockQuantity(request.getTotalStockQuantity())
                .build();
    }

    public static Product product(User currentUser, ProductUpdateRequest request, Long id) {
        return Product.builder()
                .id(id)
                .createdBy(currentUser)
                .description(request.getDescription())
                .productPrice(request.getProductPrice())
                .productName(request.getProductName())
                .build();
    }
}
