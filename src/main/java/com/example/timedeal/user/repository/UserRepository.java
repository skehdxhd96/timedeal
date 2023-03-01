package com.example.timedeal.user.repository;

import com.example.timedeal.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUserName(String username);
    Optional<User> findByUserNameAndPassword(String username, String password);
}
