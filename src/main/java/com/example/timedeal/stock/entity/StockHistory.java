package com.example.timedeal.stock.entity;

import com.example.timedeal.common.entity.baseEntity;
import com.example.timedeal.order.entity.OrderStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 인덱싱 ? => 맨 위의 값을 limit로 가져와야 하기 때문에 하는게 나을지도 ?
public class StockHistory extends baseEntity {

    @Id     @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message-id-generator")
    @GenericGenerator(
            name = "message-id-generator",
            strategy = "sequence",
            parameters = {@org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "hibernate_sequence"),
                    @org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1000"),
                    @org.hibernate.annotations.Parameter(name = AvailableSettings.PREFERRED_POOLED_OPTIMIZER, value = "pooled-lotl")}
    )
    @Column(name = "stock_history_id")
    private Long id;
    @Enumerated(value = EnumType.STRING)
    private StockHistoryType type;
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;
    private Long consumerId;
    private Long productId;
    private Long orderId;
    private int quantity;
    private double price;

    @Builder
    public StockHistory(Long id, StockHistoryType type, Long consumerId, OrderStatus orderStatus,
                        Long productId, Long orderId, int quantity, double price) {
        this.id = id;
        this.type = type;
        this.consumerId = consumerId;
        this.orderStatus = orderStatus;
        this.productId = productId;
        this.orderId = orderId;
        this.quantity = quantity;
        this.price = price;
    }
}
