package com.example.timedeal.order.entity;

import com.example.timedeal.common.entity.baseEntity;
import com.example.timedeal.common.exception.StockException;
import com.example.timedeal.product.entity.Product;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.annotations.Parameter;
import javax.persistence.*;
import java.util.Objects;
@Entity
@Table(name = "order_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Slf4j
public class OrderItem extends baseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message-id-generator")
    @GenericGenerator(
            name = "message-id-generator",
            strategy = "sequence",
            parameters = {@Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "hibernate_sequence"),
                    @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1000"),
                    @Parameter(name = AvailableSettings.PREFERRED_POOLED_OPTIMIZER, value = "pooled-lotl")}
    )
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_id")
    private Product product;
    private int itemPrice;
    private Long publishEventId;
    private int itemRealPrice;
    private int quantity;

    @Builder
    public OrderItem(Long id, Order order, Product product, int itemPrice, Long publishEventId, int itemRealPrice, int quantity) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.itemPrice = itemPrice;
        this.publishEventId = publishEventId;
        this.itemRealPrice = itemRealPrice;
        this.quantity = quantity;
    }

    public void setRealPrice(double eventDesc) {
        this.itemRealPrice = (int) (eventDesc * this.itemPrice);
    }

    public void validatedOnStock(int remaining) {

        this.product.validatedOnSell();

        if(this.getQuantity() > remaining) {
            throw new StockException("재고가 부족합니다.");
        }
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

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", order=" + order +
                ", product=" + product +
                ", itemPrice=" + itemPrice +
                ", publishEventId=" + publishEventId +
                ", itemRealPrice=" + itemRealPrice +
                ", quantity=" + quantity +
                '}';
    }
}
