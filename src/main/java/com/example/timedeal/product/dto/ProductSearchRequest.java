package com.example.timedeal.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductSearchRequest {

    private String searchKeyword;
    private String searchPrice;

    public ProductSearchRequest(String searchKeyword, String searchPrice) {
        this.searchKeyword = searchKeyword;
        this.searchPrice = searchPrice;
    }
}
