package com.example.timedeal.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@Getter
@NoArgsConstructor
public class ProductSelectRequest {

    private String eventName;
    private Pageable pageable;
    private ProductSearchRequest productSearchRequest;

    public ProductSelectRequest(String eventName, Pageable pageable, ProductSearchRequest productSearchRequest) {
        this.eventName = eventName;
        this.pageable = pageable;
        this.productSearchRequest = productSearchRequest;
    }
}
