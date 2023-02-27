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
}
