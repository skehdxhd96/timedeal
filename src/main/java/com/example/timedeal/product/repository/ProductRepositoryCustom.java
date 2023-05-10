package com.example.timedeal.product.repository;

import com.example.timedeal.product.dto.ProductSearchRequest;
import com.example.timedeal.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepositoryCustom {

    Page<Product> findAllProductOnEvent(Pageable pageable, String eventCode);

    Page<Product> findAllProducts(Pageable pageable, ProductSearchRequest productSearchRequest);

    List<Product> findProductDetailByProductIds(List<Long> productIds);
}
