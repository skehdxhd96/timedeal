package com.example.timedeal.user.service;

import com.example.timedeal.user.dto.UserLoginRequest;

import java.util.Optional;

public interface LoginService {

    void logIn(UserLoginRequest request);
    void logOut();
    String getCurrentLoginType();
}
