package com.example.timedeal.stock.entity;

import com.example.timedeal.common.entity.baseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 인덱싱 ?
public class StockHistory extends baseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_history_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private StockHistoryType type;

    private Long consumerId;
    private Long productId;
    private int quantity;
    private int price;

    @Builder
    public StockHistory(Long id, StockHistoryType type, Long consumerId, Long productId, int quantity, int price) {
        this.id = id;
        this.type = type;
        this.consumerId = consumerId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }
}
