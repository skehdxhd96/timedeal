package com.example.timedeal.order.entity;

import com.example.timedeal.common.entity.baseEntity;
import com.example.timedeal.product.entity.Product;
import com.example.timedeal.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Order extends baseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consumer_id")
    private User orderedBy;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    private int originalPrice;

    private int totalPrice;

    private Long publishEventId;

    @Builder
    public Order(Long id, User orderedBy, Product product, OrderStatus orderStatus,
                 int originalPrice, int totalPrice, Long publishEventId) {
        this.id = id;
        this.orderedBy = orderedBy;
        this.product = product;
        this.orderStatus = orderStatus;
        this.originalPrice = originalPrice;
        this.totalPrice = totalPrice;
        this.publishEventId = publishEventId;
    }
}
