package com.example.timedeal.product.dto;

import com.example.timedeal.product.entity.DealType;
import com.example.timedeal.product.entity.Product;
import com.example.timedeal.user.entity.Administrator;
import com.example.timedeal.user.entity.User;

public class ProductDtoAssembler {

    private ProductDtoAssembler() {}

    public static Product product(User currentUser, ProductSaveRequest request) {
        return Product.builder()
                .createdBy(currentUser)
                .dealType(DealType.convert(request.getDealType()))
                .description(request.getDescription())
                .productPrice(request.getProductPrice())
                .productName(request.getProductName())
                .eventEndTime(request.getEventEndTime())
                .eventStartTime(request.getEventStartTime())
                .build();
    }
}
