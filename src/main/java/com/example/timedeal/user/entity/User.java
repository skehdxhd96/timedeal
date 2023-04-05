package com.example.timedeal.user.entity;

import com.example.timedeal.common.entity.baseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Role")
public abstract class User extends baseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private UserType userType;

    private String userName;

    private String password;

    public User(Long id, String userName, String password, UserType userType) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.userType = userType;
    }
}
