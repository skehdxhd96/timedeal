package com.example.timedeal.user.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "administrator")
@PrimaryKeyJoinColumn(name = "administrator_id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(value = "ADMINISTRATOR")
@Getter
public class Administrator extends User{


    public Administrator(Long id, String userName, String password, UserType userType) {
        super(id, userName, password, userType);
    }
}
