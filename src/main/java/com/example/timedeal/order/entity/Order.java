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
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Order extends baseEntity {

    @Id     @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message-id-generator")
    @GenericGenerator(
            name = "message-id-generator",
            strategy = "sequence",
            parameters = {@org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "hibernate_sequence"),
                    @org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1000"),
                    @org.hibernate.annotations.Parameter(name = AvailableSettings.PREFERRED_POOLED_OPTIMIZER, value = "pooled-lotl")}
    )
    @Column(name = "order_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
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

    public void success() {
        this.orderStatus = OrderStatus.SUCCESS;
    }

    public void failed() {
        this.orderStatus = OrderStatus.FAILED;
    }
    public void addOrderItems(List<OrderItem> items) {
        orderItems.addAll(this, items);
        setTotalPrice();
    }

    public void setTotalPrice() {
        this.totalPrice = this.getOrderItems().getElements()
                .stream()
                .mapToInt(o -> o.getItemRealPrice() * o.getQuantity())
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

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderedBy=" + orderedBy +
                ", orderItems=" + orderItems +
                ", orderStatus=" + orderStatus +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
