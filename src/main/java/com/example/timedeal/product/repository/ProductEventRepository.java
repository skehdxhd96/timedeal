package com.example.timedeal.product.repository;

import com.example.timedeal.product.entity.ProductEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductEventRepository extends JpaRepository<ProductEvent, Long> {

    boolean existsByProductIdAndPublishEventId(Long productId, Long publishEventId);
}
