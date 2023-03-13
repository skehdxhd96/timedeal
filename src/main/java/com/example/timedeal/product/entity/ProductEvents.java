package com.example.timedeal.product.entity;

import com.example.timedeal.common.exception.BusinessException;
import com.example.timedeal.common.exception.ErrorCode;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class ProductEvents {

    @OneToMany(
            mappedBy = "publishEvent",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST,
            orphanRemoval = true
    )
    private List<ProductEvent> productEventList;

    public ProductEvents() {
        productEventList = new ArrayList<>();
    }

    public boolean contains(ProductEvent productEvent) {
        return productEventList.stream()
                .anyMatch(productEvent::equals);
    }

    public void add(ProductEvent productEvent) {
        if (contains(productEvent)) {
            throw new BusinessException(ErrorCode.ALREADY_PUBLISEHD);
        }
        productEventList.add(productEvent);
    }

    public void remove(ProductEvent productEvent) {
        if (!contains(productEvent)) {
            throw new BusinessException(ErrorCode.ALREADY_PUBLISEHD);
        }
        productEventList.remove(productEvent);
    }
}
