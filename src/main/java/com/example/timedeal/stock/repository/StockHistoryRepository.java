package com.example.timedeal.stock.repository;

import com.example.timedeal.stock.entity.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockHistoryRepository extends JpaRepository<StockHistory, Long> {
}
