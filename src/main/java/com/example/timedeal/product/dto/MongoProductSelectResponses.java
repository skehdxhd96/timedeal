package com.example.timedeal.product.dto;

import com.example.timedeal.Event.entity.PublishEvent;
import com.example.timedeal.product.entity.Product;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Document(collection = "event_product")
@Getter
public class MongoProductSelectResponses {

    @Id
    private Long id;
    private String productName;
    private int productPrice;
    private String productStatus;
    private String description;
    private Long publishEventCode; // publishEventId + Indexing
    private String eventName;
    private LocalDateTime eventStartTime;
    private LocalDateTime eventEndTime;
    private double eventDesc;
    private String eventCode; // TIMEDEAL OR COUPON OR ...

    public MongoProductSelectResponses(Product product) {

        PublishEvent publishEvent = product.getProductEvent().getPublishEvent();

        // TODO : ProductEvent null여부 검사

        this.id = product.getId();
        this.productName = product.getProductName();
        this.productPrice = product.getProductPrice();
        this.productStatus = product.getProductStatus().toString();
        this.description = product.getDescription();
        this.publishEventCode = publishEvent.getId();
        this.eventName = publishEvent.getEventName();
        this.eventStartTime = publishEvent.getEventStartTime();
        this.eventEndTime = publishEvent.getEventEndTime();
        this.eventDesc = publishEvent.getEventDesc();
        this.eventCode = publishEvent.getEventCode();
    }
}
