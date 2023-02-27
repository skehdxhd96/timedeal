package com.example.timedeal.user.entity;

import com.example.timedeal.common.entity.baseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends baseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private UserType userType;

    private String userName;

    private String password;

    @Builder
    public User(UserType userType, String userName, String password) {
        this.userType = userType;
        this.userName = userName;
        this.password = password;
    }
}
