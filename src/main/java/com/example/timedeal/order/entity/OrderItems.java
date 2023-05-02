package com.example.timedeal.order.entity;

import com.example.timedeal.common.exception.BusinessException;
import com.example.timedeal.common.exception.ErrorCode;
import com.example.timedeal.product.entity.ProductEvent;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class OrderItems {

    @OneToMany(
            mappedBy = "publishEvent",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST,
            orphanRemoval = true
    )
    private List<OrderItem> orderItemList;

    public OrderItems() {
        orderItemList = new ArrayList<>();
    }

    public boolean contains(OrderItem orderItem) {
        return orderItemList.stream()
                .anyMatch(orderItem::equals);
    }

    public void add(OrderItem orderItem) {
        if (contains(orderItem)) {
            throw new BusinessException(ErrorCode.ALREADY_IN_ORDER);
        }
        orderItemList.add(orderItem);
    }

    public void remove(OrderItem orderItem) {
        if (!contains(orderItem)) {
            throw new BusinessException(ErrorCode.NOT_IN_ORDER);
        }
        orderItemList.remove(orderItem);
    }
}
