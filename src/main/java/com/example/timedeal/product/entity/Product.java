package com.example.timedeal.product.entity;

import com.example.timedeal.common.entity.baseEntity;
import com.example.timedeal.common.exception.BusinessException;
import com.example.timedeal.common.exception.ErrorCode;
import com.example.timedeal.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Product extends baseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "administrator_id")
    private User createdBy;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_event_id")
    private ProductEvent productEvent;

    private String productName;
    private int productPrice;
    private String description;

    @Builder
    public Product(Long id, User createdBy, String productName, int productPrice, String description) {
        this.id = id;
        this.createdBy = createdBy;
        this.productName = productName;
        this.productPrice = productPrice;
        this.description = description;
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
    }

    public void assignEvent(ProductEvent productEvent) {
        this.productEvent = productEvent;
    }

    public void terminate() {
        this.productEvent = null;
    }
}
