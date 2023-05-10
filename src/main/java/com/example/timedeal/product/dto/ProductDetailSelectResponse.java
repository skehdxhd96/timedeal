package com.example.timedeal.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductDetailSelectResponse {

    private Long id;
    private String productName;
    private int productPrice;
    private String description;

}
