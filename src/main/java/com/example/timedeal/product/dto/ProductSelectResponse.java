package com.example.timedeal.product.dto;

import com.example.timedeal.product.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductSelectResponse {

    private Long id;
    private String productName;
    private int productPrice;
    private String description;

    public ProductSelectResponse(Product product) {
        this.id = product.getId();
        this.productName = product.getProductName();
        this.productPrice = product.getProductPrice();
        this.description = product.getDescription();
    }
}
