package com.example.timedeal.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    DUPLICATED_USERNAME("500", "중복된 아이디 입니다.", "DUPLICATED_USERNAME"),
    USER_NOT_FOUND("500", "중복된 아이디 입니다.", "USER_NOT_FOUND");

    private final String code;
    private final String message;
    private final String status;

    ErrorCode(String code, String message, String status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
