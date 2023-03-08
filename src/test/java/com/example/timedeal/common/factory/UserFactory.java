package com.example.timedeal.common.factory;

import com.example.timedeal.common.mockUser.MockConsumer;
import com.example.timedeal.user.dto.UserSaveRequest;
import com.example.timedeal.user.dto.UserSaveResponse;
import com.example.timedeal.user.entity.User;

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
}
