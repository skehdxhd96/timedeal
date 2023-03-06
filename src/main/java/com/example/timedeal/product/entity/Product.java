package com.example.timedeal.product.entity;

import com.example.timedeal.common.entity.baseEntity;
import com.example.timedeal.user.entity.Administrator;
import com.example.timedeal.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Enumerated(value = EnumType.STRING)
    private DealType dealType;

    private String productName;
    private int productPrice;
    private LocalDateTime eventStartTime;
    private LocalDateTime eventEndTime;
    private String description;

    @Builder
    public Product(User createdBy, DealType dealType, String productName, int productPrice,
                   LocalDateTime eventStartTime, LocalDateTime eventEndTime, String description) {
        this.createdBy = createdBy;
        this.dealType = dealType;
        this.productName = productName;
        this.productPrice = productPrice;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.description = description;
    }
}
