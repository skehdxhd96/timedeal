package com.example.timedeal.product.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ProductUpdateRequest {

    private String productName;
    private int productPrice;
    private String description;

    // Event관련 데이터와 Stock관련 데이터는 상품 수정 api로 수정할 수 없다.

    @Builder
    public ProductUpdateRequest(String productName, int productPrice, String description) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.description = description;
    }
}
