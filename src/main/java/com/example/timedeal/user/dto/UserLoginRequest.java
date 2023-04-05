package com.example.timedeal.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserLoginRequest {

    private String username;
    private String password;

    @Builder
    public UserLoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
