package com.example.timedeal.user.dto;

import com.example.timedeal.common.dto.AuthUser;
import com.example.timedeal.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSelectResponse {

    private Long userId;
    private String userName;
    private String userType;

    private UserSelectResponse(Long userId, String userName, String userType) {
        this.userId = userId;
        this.userName = userName;
        this.userType = userType;
    }

    public static UserSelectResponse of(User user) {
        return new UserSelectResponse(user.getId(), user.getUserName(), user.getUserType().name());
    }

    public static UserSelectResponse of(AuthUser user) {
        return new UserSelectResponse(user.getId(), user.getUsername(), user.getUsertype());
    }
}
