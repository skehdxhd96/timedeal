package com.example.timedeal.order.entity;

import com.example.timedeal.common.exception.BusinessException;
import com.example.timedeal.common.exception.ErrorCode;
import com.example.timedeal.product.entity.ProductEvent;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Embeddable
public class OrderItems {

    @OneToMany(
            mappedBy = "order",
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

    public void addAll(Order order, List<OrderItem> orderItems) {
        orderItems.forEach(this::validatedOrderItemWhenAdd);
        orderItemList.addAll(orderItems);
    }

    public List<OrderItem> getElements() {
        return Collections.unmodifiableList(this.orderItemList);
    }

    public void add(OrderItem orderItem) {
        validatedOrderItemWhenAdd(orderItem);
        orderItemList.add(orderItem);
    }

    public void remove(OrderItem orderItem) {
        validatedOrderItemWhenRemove(orderItem);
        orderItemList.remove(orderItem);
    }

    public void validatedOrderItemWhenRemove(OrderItem orderItem) {
        if (!contains(orderItem)) {
            throw new BusinessException(ErrorCode.NOT_IN_ORDER);
        }
    }

    public void validatedOrderItemWhenAdd(OrderItem orderItem) {
        if (contains(orderItem)) {
            throw new BusinessException(ErrorCode.ALREADY_IN_ORDER);
        }
    }
}
