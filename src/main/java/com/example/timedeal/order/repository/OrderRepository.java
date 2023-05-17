package com.example.timedeal.order.repository;

import com.example.timedeal.order.entity.Order;
import com.example.timedeal.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByOrderedBy(User orderedBy);

    @Query("select distinct o from OrderItem oi " +
            "join fetch Order o " +
            "join fetch User u " +
            "where oi.product.id = :productId")
    List<Order> findOrderByProductId(Long productId);
    Optional<Order> findOrderByIdAndAndOrderStatus(Long orderId, String orderStatus);
}
