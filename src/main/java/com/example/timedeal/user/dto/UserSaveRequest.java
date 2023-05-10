package com.example.timedeal.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class UserSaveRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String address;

    @Builder
    public UserSaveRequest(String username, String password, String address) {
        this.username = username;
        this.password = password;
        this.address = address;
    }
}
