package com.example.timedeal.Event.repository;

import com.example.timedeal.Event.entity.ProductEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductEventRepository extends JpaRepository<ProductEvent, Long> {
}
