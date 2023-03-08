package com.example.timedeal.user.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "consumer")
@PrimaryKeyJoinColumn(name = "customer_id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(value = "CUSTOMER")
@Getter
public class Consumer extends User{

    private String address;

    @Builder
    public Consumer(Long id, String userName, String password, UserType userType, String address) {
        super(id, userName, password, userType);
        this.address = address;
    }
}
