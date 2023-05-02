package com.example.timedeal.user.service;

import com.example.timedeal.common.dto.AuthUser;
import com.example.timedeal.user.dto.UserLoginRequest;
import com.example.timedeal.user.dto.UserSelectResponse;

import java.util.Optional;

public interface LoginService {

    UserSelectResponse logIn(UserLoginRequest request);
    void logOut();
    AuthUser getCurrentUser();
}
