package com.example.timedeal.order.entity;

import com.example.timedeal.common.entity.baseEntity;
import com.example.timedeal.product.entity.Product;
import com.example.timedeal.product.entity.ProductEvent;
import com.example.timedeal.product.entity.ProductEvents;
import com.example.timedeal.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

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
    @Embedded
    private OrderItems orderItems;
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;
    private int totalPrice;

    // TODO : totalPrice

    @Builder
    public Order(Long id, User orderedBy, OrderStatus orderStatus, int totalPrice) {
        this.id = id;
        this.orderedBy = orderedBy;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.orderItems = new OrderItems();
    }
    public void addOrderItems(List<OrderItem> items) {
        orderItems.addAll(this, items);
        setTotalPrice();
    }

    public void setTotalPrice() {
        this.totalPrice = this.getOrderItems().getElements()
                .stream()
                .mapToInt(OrderItem::getItemRealPrice)
                .sum();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
