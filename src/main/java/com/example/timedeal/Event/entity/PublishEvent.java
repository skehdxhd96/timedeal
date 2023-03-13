package com.example.timedeal.Event.entity;

import com.example.timedeal.common.entity.baseEntity;
import com.example.timedeal.common.exception.BusinessException;
import com.example.timedeal.common.exception.ErrorCode;
import com.example.timedeal.product.entity.Product;
import com.example.timedeal.product.entity.ProductEvent;
import com.example.timedeal.product.entity.ProductEvents;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "publish_event")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PublishEvent extends baseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "publish_event_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @Embedded
    private ProductEvents productEvents;

    private String eventName;
    private LocalDateTime eventStartTime;
    private LocalDateTime eventEndTime;
    private int eventDesc;

    @Builder
    public PublishEvent(String eventName, LocalDateTime eventStartTime, LocalDateTime eventEndTime, int eventDesc) {
        this.eventName = eventName;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.eventDesc = eventDesc;
    }

    public void setEvent(Event event) {

        if(this.event != null) {
            throw new BusinessException(ErrorCode.ALREADY_PUBLISEHD);
        }

        this.event = event;
    }

    public void register(Product product) {

        ProductEvent productEvent = new ProductEvent(product, this);
        productEvents.add(productEvent);
    }

    public void terminate(Product product) {

        ProductEvent productEvent = new ProductEvent(product, this);
        productEvents.remove(productEvent);
    }
}
