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
    private String eventName;
    private LocalDateTime eventStartTime;
    private LocalDateTime eventEndTime;
    private int eventDesc;

    private ProductSelectResponse(Product product) {

        PublishEvent publishEvent = product.getProductEvent().getPublishEvent();

        this.id = product.getId();
        this.productName = product.getProductName();
//        this.productPrice = product.getProductPrice();
        this.description = product.getDescription();
        this.eventName = publishEvent.getEventName();
        this.eventStartTime = publishEvent.getEventStartTime();
        this.eventEndTime = publishEvent.getEventEndTime();
        this.eventDesc = publishEvent.getEventDesc();
    }

    public static ProductSelectResponse of(Product product) {
        return new ProductSelectResponse(product);
    }
}
