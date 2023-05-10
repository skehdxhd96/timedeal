package com.example.timedeal.product.entity;

import com.example.timedeal.Event.entity.PublishEvent;
import com.example.timedeal.common.entity.baseEntity;
import com.example.timedeal.common.exception.BusinessException;
import com.example.timedeal.common.exception.ErrorCode;
import com.example.timedeal.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Slf4j
public class Product extends baseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "administrator_id")
    private User createdBy;

    @OneToOne(mappedBy = "product")
    private ProductEvent productEvent;

    @Enumerated(value = EnumType.STRING)
    private ProductStatus productStatus;

    private String productName;
    private double productPrice;
    private String description;
    private int totalStockQuantity;

    @Builder
    public Product(Long id, User createdBy, String productName, double productPrice, String description, int totalStockQuantity) {
        this.id = id;
        this.createdBy = createdBy;
        this.productName = productName;
        this.productPrice = productPrice;
        this.description = description;
        this.totalStockQuantity = totalStockQuantity;
    }

    public void validatedInEvent() {
        if(this.productEvent != null) {
            this.productEvent.getPublishEvent().isInProgress();
            log.info("{} 상품의 이벤트 기간에 벗어남. 상품 아이디 : {}" , this.productName, this.id);
        }
    }

    public void validateOnEvent() {
        if(this.productEvent != null) {
            throw new BusinessException(ErrorCode.ALREADY_HAS_EVENT);
        }
    }

    public void update(Product product) {

        validateOnEvent();

        this.productName = product.getProductName();
        this.productPrice = product.getProductPrice();
        this.description = product.getDescription();
        // 재고 수량 업데이트 쳐도 되나 ..?
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
