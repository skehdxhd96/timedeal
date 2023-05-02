package com.example.timedeal.common.factory;

import com.example.timedeal.common.dto.AuthUser;
import com.example.timedeal.common.mockUser.MockConsumer;
import com.example.timedeal.user.dto.UserLoginRequest;
import com.example.timedeal.user.dto.UserSaveRequest;
import com.example.timedeal.user.dto.UserSaveResponse;
import com.example.timedeal.user.dto.UserSelectResponse;
import com.example.timedeal.user.entity.Consumer;
import com.example.timedeal.user.entity.User;
import com.example.timedeal.user.entity.UserType;

public class UserFactory {

    private UserFactory() {}

    public static UserSaveRequest userSaveRequest() {
        return UserSaveRequest.builder()
                .username("test")
                .password("1234")
                .address("korea")
                .build();
    }
    public static UserSaveResponse userSaveResponse() {
        return UserSaveResponse.builder()
                .id(1L)
                .username("test")
                .build();
    }

    public static Consumer consumer() {
        return Consumer.builder()
                .userName("test")
                .password("1234")
                .address("korea")
                .userType(UserType.CONSUMER)
                .id(1L)
                .build();
    }

    public static UserLoginRequest userLoginRequest() {
        return UserLoginRequest.builder()
                .username("test")
                .password("1234")
                .build();
    }

    public static UserSelectResponse userSelectResponse() {
        consumer();
        return UserSelectResponse.of(consumer());
    }
}
