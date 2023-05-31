package com.example.timedeal.product.entity;

import com.example.timedeal.common.entity.baseEntity;
import com.example.timedeal.common.exception.BusinessException;
import com.example.timedeal.common.exception.ErrorCode;
import com.example.timedeal.common.exception.StockException;
import com.example.timedeal.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Slf4j
public class Product extends baseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message-id-generator")
//    @GenericGenerator(
//            name = "message-id-generator",
//            strategy = "sequence",
//            parameters = {@org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "hibernate_sequence"),
//                    @org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1000"),
//                    @org.hibernate.annotations.Parameter(name = AvailableSettings.PREFERRED_POOLED_OPTIMIZER, value = "pooled-lotl")}
//    )
    @Column(name = "product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User createdBy;

    @OneToOne(mappedBy = "product")
    private ProductEvent productEvent;

    @Enumerated(value = EnumType.STRING)
    private ProductStatus productStatus;

    private String productName;
    private int productPrice;
    private String description;
    private int totalStockQuantity;

    @Builder
    public Product(Long id, User createdBy, ProductEvent productEvent, ProductStatus productStatus, String productName, int productPrice, String description, int totalStockQuantity) {
        this.id = id;
        this.createdBy = createdBy;
        this.productEvent = productEvent;
        this.productStatus = productStatus;
        this.productName = productName;
        this.productPrice = productPrice;
        this.description = description;
        this.totalStockQuantity = totalStockQuantity;
    }

    public void soldOut() {
        this.productStatus = ProductStatus.SOLD_OUT;
    }

    public void validated() {
        validatedInEvent();
        validatedOnSell();
    }

    public void validatedInEvent() {
        if(this.productEvent != null) {
            this.productEvent.getPublishEvent().isInProgress();
            log.info("{} 상품의 이벤트 기간에 벗어남. 상품 아이디 : {}" , this.productName, this.id);
        }
    }

    public void validatedOnSell() {
        if(this.productStatus == ProductStatus.OFF) {
            throw new StockException("판매중인 상품이 아닙니다.");
        }

        if(this.productStatus == ProductStatus.SOLD_OUT) {
            throw new StockException("품절된 상품입니다.");
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

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", createdBy=" + createdBy +
                ", productEvent=" + productEvent +
                ", productStatus=" + productStatus +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", description='" + description + '\'' +
                ", totalStockQuantity=" + totalStockQuantity +
                '}';
    }
}
