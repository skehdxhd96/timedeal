package com.example.timedeal.Event.repository;

import com.example.timedeal.Event.entity.PublishEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublishEventRepository extends JpaRepository<PublishEvent, Long> {
}
