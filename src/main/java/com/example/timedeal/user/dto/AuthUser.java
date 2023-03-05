package com.example.timedeal.user.dto;

import com.example.timedeal.user.entity.User;
import com.example.timedeal.user.entity.UserType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthUser {

    private Long id;
    private String username;
    private String usertype;

    private AuthUser(Long id, String username, String usertype) {
        this.id = id;
        this.username = username;
        this.usertype = usertype;
    }

    public static AuthUser of(User user) {
        return new AuthUser(user.getId(), user.getUserName(), user.getUserType().name());
    }
}
