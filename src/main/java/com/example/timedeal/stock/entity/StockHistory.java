package com.example.timedeal.stock.entity;

import com.example.timedeal.common.entity.baseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 인덱싱 ? => 맨 위의 값을 limit로 가져와야 하기 때문에 하는게 나을지도 ?
public class StockHistory extends baseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_history_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private StockHistoryType type;

    private Long consumerId;
    private Long productId;
    private UUID purchaseCode;
    private int quantity;
    private double price;

    @Builder
    public StockHistory(Long id, StockHistoryType type, Long consumerId,
                        Long productId, UUID purchaseCode, int quantity, double price) {
        this.id = id;
        this.type = type;
        this.consumerId = consumerId;
        this.productId = productId;
        this.purchaseCode = purchaseCode;
        this.quantity = quantity;
        this.price = price;
    }
}
