package com.example.timedeal.product.entity;

import com.example.timedeal.Event.entity.PublishEvent;
import com.example.timedeal.common.entity.baseEntity;
import com.example.timedeal.common.exception.BusinessException;
import com.example.timedeal.common.exception.ErrorCode;
import com.example.timedeal.product.entity.Product;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "product_event")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductEvent extends baseEntity {

    // TODO : 굳이 엔티티 정보들을 갖고있을 필요가 없음. 연관관계를 끊고 id만 갖고 있어도 될 것 같다.

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_event_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publish_event_id")
    private PublishEvent publishEvent;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public ProductEvent(Product product, PublishEvent publishEvent) {
        this.id = null;
        this.product = product;
        this.publishEvent = publishEvent;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductEvent)) return false;
        ProductEvent that = (ProductEvent) o;
//        return Objects.equals(publishEvent, that.publishEvent) && Objects.equals(product, that.product);
        return publishEvent.equals(that.publishEvent) && product.equals(that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(publishEvent, product);
    }
}
