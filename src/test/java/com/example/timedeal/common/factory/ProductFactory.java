package com.example.timedeal.common.factory;

import com.example.timedeal.product.dto.ProductEventRequest;
import com.example.timedeal.product.dto.ProductSaveRequest;
import com.example.timedeal.product.dto.ProductSelectResponse;
import com.example.timedeal.product.dto.ProductUpdateRequest;
import com.example.timedeal.product.entity.Product;
import com.example.timedeal.product.entity.ProductEvent;
import com.example.timedeal.product.entity.ProductEvents;

import java.time.LocalDateTime;

public class ProductFactory {

    private ProductFactory() {}

    public static ProductSaveRequest productSaveRequest() {
        return ProductSaveRequest.builder()
                .productName("test product 1")
                .productPrice(10000)
                .description("test product 1 desc")
                .totalStockQuantity(100)
                .build();
    }

    public static ProductUpdateRequest productUpdateRequest() {

        return ProductUpdateRequest.builder()
                .productName("test product 1 update")
                .productPrice(20000)
                .description("test product updated")
                .build();
    }

    public static ProductEventRequest productEventRequest() {
        return new ProductEventRequest(1L);
    }

    public static ProductSelectResponse productSelectResponse() {
        return ProductSelectResponse.of(product());
    }

    public static ProductSelectResponse productUpdateSelectResponse() {

        ProductUpdateRequest productUpdateRequest = productUpdateRequest();

        Product newProduct = Product.builder()
                .id(1L)
                .createdBy(UserFactory.administrator())
                .productName(productUpdateRequest.getProductName())
                .productPrice(productUpdateRequest.getProductPrice())
                .description(productUpdateRequest.getDescription())
                .totalStockQuantity(100)
                .build();

        return ProductSelectResponse.of(newProduct);
    }

    public static Product product() {
        return Product.builder()
                .id(1L)
                .createdBy(UserFactory.administrator())
                .productName("test product 1")
                .productPrice(10000)
                .description("test product 1 desc")
                .totalStockQuantity(100)
                .build();
    }

    public static ProductEvents productEvents() {
        return new ProductEvents();
    }
}
