package com.example.timedeal.product.repository;

import com.example.timedeal.product.dto.ProductEventRequest;
import com.example.timedeal.product.entity.Product;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import static com.example.timedeal.product.entity.QProduct.product;
import static com.example.timedeal.Event.entity.QProductEvent.productEvent;
import static com.example.timedeal.Event.entity.QPublishEvent.publishEvent;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Product> findAllProductOnEvent(String eventName, LocalDateTime now) {

        return queryFactory.selectFrom(product)
                .leftJoin(product.productEvent, productEvent).fetchJoin()
                .leftJoin(productEvent.publishEvent, publishEvent).fetchJoin()
                .where(publishEvent.eventName.eq(eventName)
                        .and(publishEvent.eventStartTime.before(now))
                        .and(publishEvent.eventEndTime.after(now)))
                .fetch();
    }
}
