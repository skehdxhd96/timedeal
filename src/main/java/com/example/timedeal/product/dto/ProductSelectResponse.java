package com.example.timedeal.product.dto;

import com.example.timedeal.Event.entity.PublishEvent;
import com.example.timedeal.product.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ProductSelectResponse {

    private Long id;
    private String productName;
    private int productPrice;
    private String description;

    protected ProductSelectResponse(Product product) {

        this.id = product.getId();
        this.productName = product.getProductName();
        this.productPrice = product.getProductPrice();
        this.description = product.getDescription();

    }

    protected ProductSelectResponse(Long id, String productName, int productPrice, String description) {
        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
        this.description = description;
    }

    public static ProductSelectResponse of(Product product) {
        return new ProductSelectResponse(product);
    }
}
