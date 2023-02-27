package com.example.timedeal.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class UserSaveRequest {

    @NotBlank
    private String userName;

    @NotBlank
    private String password;

    @NotBlank
    private String userType;

    @Builder
    public UserSaveRequest(String userName, String password, String userType) {
        this.userName = userName;
        this.password = password;
        this.userType = userType;
    }
}
