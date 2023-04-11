package com.example.timedeal.product.repository;

import com.example.timedeal.common.entity.RestPage;
import com.example.timedeal.product.dto.ProductSearchRequest;
import com.example.timedeal.product.entity.Product;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import static com.example.timedeal.product.entity.QProduct.product;
import static com.example.timedeal.product.entity.QProductEvent.productEvent;
import static com.example.timedeal.Event.entity.QPublishEvent.publishEvent;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Product> findAllProductOnEvent(Pageable pageable, String eventName) {

        List<Product> products = queryFactory.selectFrom(product)
                .innerJoin(product.productEvent, productEvent).fetchJoin()
                .innerJoin(productEvent.publishEvent, publishEvent).fetchJoin()
                .where(publishEvent.eventName.eq(eventName)
                        .and(publishEvent.eventStartTime.before(LocalDateTime.now()))
                        .and(publishEvent.eventEndTime.after(LocalDateTime.now())))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        Long count = queryFactory.select(product.count())
                .from(product)
                .innerJoin(product.productEvent, productEvent)
                .innerJoin(productEvent.publishEvent, publishEvent)
                .where(publishEvent.eventName.eq(eventName)
                                .and(publishEvent.eventStartTime.before(LocalDateTime.now()))
                                .and(publishEvent.eventEndTime.after(LocalDateTime.now())))
                .fetchFirst();

        return new RestPage(PageableExecutionUtils.getPage(products, pageable, () -> count));
    }

    @Override
    public Page<Product> findAllProducts(Pageable pageable, ProductSearchRequest productSearchRequest) {
        List<Product> products = queryFactory.selectFrom(product)
                .where(eqProductName(productSearchRequest.getSearchKeyword()),
                        (notMoreThanPrice(productSearchRequest.getSearchPrice())))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        Long count = queryFactory.select(product.count())
                .from(product)
                .where(eqProductName(productSearchRequest.getSearchKeyword()),
                        (notMoreThanPrice(productSearchRequest.getSearchPrice())))
                .fetchFirst();

        return PageableExecutionUtils.getPage(products, pageable, () -> count);
    }

    private BooleanExpression eqProductName(String searchKeyword) {

        if(StringUtils.isBlank(searchKeyword)) {
            return null;
        }

        return product.productName.startsWith(searchKeyword); // Indexing 필요. contains는 안됨.
    }

    private BooleanExpression notMoreThanPrice(String productPrice) {

        if(StringUtils.isBlank(productPrice)) {
            return null;
        }

        return product.productPrice.loe(Integer.valueOf(productPrice));
    }
}
