package com.example.timedeal.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductSearchRequest {

    private String searchKeyword;
    private int searchPrice;

    public ProductSearchRequest(String searchKeyword, int searchPrice) {
        this.searchKeyword = searchKeyword;
        this.searchPrice = searchPrice;
    }
}
