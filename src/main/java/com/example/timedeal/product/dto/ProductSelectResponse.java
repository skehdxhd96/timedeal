package com.example.timedeal.product.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ProductSelectResponse {

    private Long id;
    private String dealType;
    private String productName;
    private int productPrice;
    private LocalDateTime eventStartTime;
    private LocalDateTime eventEndTime;
    private String description;

    @Builder
    public ProductSelectResponse(Long id, String dealType, String productName, int productPrice,
                                 LocalDateTime eventStartTime, LocalDateTime eventEndTime, String description) {
        this.id = id;
        this.dealType = dealType;
        this.productName = productName;
        this.productPrice = productPrice;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.description = description;
    }
}
