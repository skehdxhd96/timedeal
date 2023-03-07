package com.example.timedeal.Event.entity;

import com.example.timedeal.Event.entity.PublishEvent;
import com.example.timedeal.common.entity.baseEntity;
import com.example.timedeal.product.entity.Product;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "product_event")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductEvent extends baseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_event_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publish_event_id")
    private PublishEvent publishEvent;

    @OneToOne(mappedBy = "productEvent")
    private Product product;
}
