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
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "publish_event")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class PublishEvent extends baseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message-id-generator")
//    @GenericGenerator(
//            name = "message-id-generator",
//            strategy = "sequence",
//            parameters = {@org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "hibernate_sequence"),
//                    @org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1000"),
//                    @org.hibernate.annotations.Parameter(name = AvailableSettings.PREFERRED_POOLED_OPTIMIZER, value = "pooled-lotl")}
//    )
    @Column(name = "publish_event_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;
    @Embedded
    private ProductEvents productEvents;
    @Enumerated(value = EnumType.STRING)
    private EventStatus eventStatus;
    private String eventName;
    private LocalDateTime eventStartTime;
    private LocalDateTime eventEndTime;
    private double eventDesc;
    private String eventCode;

    @Builder
    public PublishEvent(Long id, Event event, ProductEvents productEvents, EventStatus eventStatus, String eventName,
                        LocalDateTime eventStartTime, LocalDateTime eventEndTime, double eventDesc, String eventCode) {
        this.id = id;
        this.event = event;
        this.productEvents = productEvents;
        this.eventStatus = eventStatus;
        this.eventName = eventName;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.eventDesc = eventDesc;
        this.eventCode = eventCode;
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

    public void isInProgress() {

        LocalDateTime now = LocalDateTime.now();

        if(this.eventStartTime.isAfter(now)
            || this.eventEndTime.isBefore(now)) {

            log.error("현재 {} 이벤트 기간에 벗어남. 이벤트 아이디 : {}", this.eventName, this.id);

            throw new BusinessException(ErrorCode.NOT_IN_PROGRESSING);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PublishEvent)) return false;
        PublishEvent that = (PublishEvent) o;
        return Objects.equals(id, that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
