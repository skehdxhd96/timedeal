package com.example.timedeal.user.entity;

import com.example.timedeal.common.entity.baseEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Role")
public abstract class User extends baseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message-id-generator")
//    @GenericGenerator(
//            name = "message-id-generator",
//            strategy = "sequence",
//            parameters = {@org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "hibernate_sequence"),
//                    @org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1000"),
//                    @org.hibernate.annotations.Parameter(name = AvailableSettings.PREFERRED_POOLED_OPTIMIZER, value = "pooled-lotl")}
//    )
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_type")
    @Enumerated(value = EnumType.STRING)
    private UserType userType;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_password")
    private String password;

    public User(Long id, String userName, String password, UserType userType) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userType=" + userType +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
