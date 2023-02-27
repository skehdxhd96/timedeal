package com.example.timedeal.user.entity;

import com.example.timedeal.common.entity.baseEntity;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Getter
public class User extends baseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private UserType userType;

    private String username;

    private String password;

    private String sessionId;
}
