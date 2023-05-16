package com.example.timedeal.order.repository;

import com.example.timedeal.order.entity.Order;
import com.example.timedeal.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByOrderedBy(User orderedBy);

    @Query("select o from Order o ")

}
