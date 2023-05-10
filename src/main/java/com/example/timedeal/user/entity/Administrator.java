package com.example.timedeal.user.entity;

import com.example.timedeal.Event.entity.Event;
import com.example.timedeal.product.entity.Product;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "administrator")
@PrimaryKeyJoinColumn(name = "administrator_id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(value = "ADMINISTRATOR")
@Getter
public class Administrator extends User{

    @OneToMany(mappedBy = "createdBy")
    private List<Product> products;

    @OneToMany(mappedBy = "createdBy")
    private List<Event> events;

    @Builder
    public Administrator(Long id, String userName, String password, UserType userType) {
        super(id, userName, password, userType);
    }
}
