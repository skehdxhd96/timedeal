package com.example.timedeal.product.dto;

import com.example.timedeal.Event.entity.PublishEvent;
import com.example.timedeal.product.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ProductEventSelectResponse extends ProductSelectResponse{

    private String eventName;
    private LocalDateTime eventStartTime;
    private LocalDateTime eventEndTime;
    private int eventDesc;

    private ProductEventSelectResponse(Product product) {

        super(product);

        PublishEvent publishEvent = product.getProductEvent().getPublishEvent();
        this.eventName = publishEvent.getEventName();
        this.eventStartTime = publishEvent.getEventStartTime();
        this.eventEndTime = publishEvent.getEventEndTime();
        this.eventDesc = publishEvent.getEventDesc();
    }

    public static ProductEventSelectResponse of(Product product) {
        return new ProductEventSelectResponse(product);
    }
}
