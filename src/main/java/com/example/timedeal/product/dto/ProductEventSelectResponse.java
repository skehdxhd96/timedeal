package com.example.timedeal.product.dto;

import com.example.timedeal.Event.entity.PublishEvent;
import com.example.timedeal.product.entity.Product;
import com.example.timedeal.product.entity.ProductEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ProductEventSelectResponse extends ProductSelectResponse{

    private String eventName;
    private LocalDateTime eventStartTime;
    private LocalDateTime eventEndTime;
    private double eventDesc;

    private ProductEventSelectResponse(ProductEvent productEvent) {

        super(productEvent.getProduct().getId(), productEvent.getProduct().getProductName(), productEvent.getProduct().getProductPrice(), productEvent.getProduct().getDescription());

        PublishEvent publishEvent = productEvent.getPublishEvent();
        this.eventName = publishEvent.getEventName();
        this.eventStartTime = publishEvent.getEventStartTime();
        this.eventEndTime = publishEvent.getEventEndTime();
        this.eventDesc = publishEvent.getEventDesc();
    }

    public static ProductEventSelectResponse of(ProductEvent productEvent) {
        return new ProductEventSelectResponse(productEvent);
    }
}
