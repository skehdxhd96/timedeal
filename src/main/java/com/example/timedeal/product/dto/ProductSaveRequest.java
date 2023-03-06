package com.example.timedeal.product.dto;

import com.example.timedeal.product.entity.DealType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
    private String dealType;

    @NotBlank
    private String description;

    // TODO : dealType에 따라 없어도 되는 컬럼. LocalDateTime 포맷 설정 필요할 듯 함.

    private LocalDateTime eventStartTime;

    private LocalDateTime eventEndTime;

    @Builder
    public ProductSaveRequest(String productName, int productPrice, String dealType, String description,
                              LocalDateTime eventStartTime, LocalDateTime eventEndTime) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.dealType = dealType;
        this.description = description;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
    }
}
