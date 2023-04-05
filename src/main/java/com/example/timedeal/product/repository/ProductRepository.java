package com.example.timedeal.product.repository;

import com.example.timedeal.product.dto.ProductSearchRequest;
import com.example.timedeal.product.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
    @Query("select p from Product p " +
            "left join fetch p.productEvent pde " +
            "left join fetch pde.publishEvent ppe " +
            "where p.id = :id")
    Optional<Product> findProductDetailById(Long id);
}
