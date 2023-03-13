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

    @OneToOne(mappedBy = "product", cascade = CascadeType.REMOVE)
    private ProductEvent productEvent;

    @Enumerated(value = EnumType.STRING)
    private ProductStatus productStatus;

    private String productName;
    private int productPrice;
    private String description;
    private int totalStock;

    @Builder
    public Product(Long id, User createdBy, String productName, int productPrice, String description, int totalStock) {
        this.id = id;
        this.createdBy = createdBy;
        this.productName = productName;
        this.productPrice = productPrice;
        this.description = description;
        this.totalStock = totalStock;
    }

    public void update(Product product) {

        validatedOnEvent();

        this.productName = product.getProductName();
        this.productPrice = product.getProductPrice();
        this.description = product.getDescription();
        // 재고 수량 업데이트 쳐도 되나 ..?
    }

    public void validatedOnEvent() {

        if(this.productEvent != null) {
            throw new BusinessException(ErrorCode.ALREADY_HAS_EVENT);
        }
    }

    public void validatedStock() {

        if(this.productEvent == null) validatedGeneralProduct();
        else validatedEventProduct();
    }

    private void validatedEventProduct() {

    }

    private void validatedGeneralProduct() {

    }

    public void assignEvent(ProductEvent productEvent) {
        this.productEvent = productEvent;
    }

    public void terminateEvent() {
        this.productEvent = null;
    }
}
