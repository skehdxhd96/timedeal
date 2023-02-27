package com.example.timedeal.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserType {

    CONSUMER("CONSUMER", "소비자"),
    ADMINISTRATOR("ADMINISTRATOR", "관리자");

    private final String key;
    private final String value;

    public static UserType of(String userType) {

        switch(userType) {
            case "CONSUMER":
                return UserType.CONSUMER;
            case "ADMINISTRATOR" :
                return UserType.ADMINISTRATOR;
            default :
                throw new RuntimeException("존재하지 않는 유저타입 입니다.");
        }
    }
}
