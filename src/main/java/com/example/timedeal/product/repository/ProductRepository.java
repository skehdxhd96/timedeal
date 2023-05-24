package com.example.timedeal.product.repository;

import com.example.timedeal.product.dto.ProductSearchRequest;
import com.example.timedeal.product.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.example.timedeal.Event.entity.QPublishEvent.publishEvent;
import static com.example.timedeal.product.entity.QProduct.product;
import static com.example.timedeal.product.entity.QProductEvent.productEvent;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
    @Query("select p from Product p " +
            "left join fetch p.productEvent pde " +
            "left join fetch pde.publishEvent ppe " +
            "where p.id = :id")
    Optional<Product> findProductDetailById(@Param("id") Long id);
    @Query("select p from Product p " +
            "left join fetch p.productEvent pe " +
            "left join fetch pe.publishEvent " +
            "where p.id in :productIds")
    List<Product> findProductDetailByProductIds(@Param("productIds") List<Long> productIds);
}
