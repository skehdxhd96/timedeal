package com.example.timedeal.user.entity;

import com.example.timedeal.order.entity.Order;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "consumer")
@PrimaryKeyJoinColumn(name = "consumer_id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(value = "CONSUMER")
@Getter
public class Consumer extends User{

    @OneToMany(mappedBy = "orderedBy")
    private List<Order> orders = new ArrayList<>();

    private String address;

    @Builder
    public Consumer(Long id, String userName, String password, UserType userType, String address) {
        super(id, userName, password, userType);
        this.address = address;
    }
}
