package com.example.timedeal.user.entity;

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
}