package com.example.timedeal.user.dto;

import com.example.timedeal.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSaveResponse {

    private Long id;
    private String username;

    @Builder
    private UserSaveResponse(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public static UserSaveResponse of(User user) {
        return new UserSaveResponse(user.getId(), user.getUserName());
    }
}
