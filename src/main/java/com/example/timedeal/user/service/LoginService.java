package com.example.timedeal.user.service;

import com.example.timedeal.user.dto.UserLoginRequest;

public interface LoginService {

    public void logIn(UserLoginRequest request);
    public void logOut();
}
