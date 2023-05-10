package com.example.timedeal.product.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ProductSaveRequest {

    @NotBlank
    private String productName;

    @NotNull
    private int productPrice;

    @NotBlank
    private String description;

    @Builder
    public ProductSaveRequest(String productName, int productPrice, String description) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.description = description;
    }
}
