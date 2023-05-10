package com.example.timedeal.order.entity;

import com.example.timedeal.common.entity.baseEntity;
import com.example.timedeal.product.entity.Product;
import com.example.timedeal.product.entity.ProductEvent;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "order_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderItem extends baseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    private double itemPrice;
    private Long publishEventId;
    private double itemRealPrice;
    private int quantity;

    @Builder
    public OrderItem(Long id, Order order, Product product, double itemPrice, Long publishEventId, double itemRealPrice, int quantity) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.itemPrice = itemPrice;
        this.publishEventId = publishEventId;
        this.itemRealPrice = itemRealPrice;
        this.quantity = quantity;
    }

    public void setRealPrice(double eventDesc) {
        this.itemRealPrice = eventDesc * this.itemPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem)) return false;
        OrderItem that = (OrderItem) o;
        return order.equals(that.order) && product.equals(that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, product);
    }
}
