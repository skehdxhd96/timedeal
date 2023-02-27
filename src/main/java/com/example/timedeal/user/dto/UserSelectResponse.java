package com.example.timedeal.user.dto;

import com.example.timedeal.user.entity.User;
import lombok.Getter;

@Getter
public class UserSelectResponse {

    private String userName;
    private String userType;

    private UserSelectResponse(String userName, String userType) {
        this.userName = userName;
        this.userType = userType;
    }

    public static UserSelectResponse of(User user) {
        return new UserSelectResponse(user.getUserName(), user.getUserType().getValue());
    }
}
