package com.example.timedeal.user.dto;

import lombok.Getter;

@Getter
public class UserLoginRequest {

    private String username;
    private String password;

    public UserLoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
