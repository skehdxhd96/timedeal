package com.example.timedeal.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    USER_NOT_FOUND("500", "유저를 찾을 수 없습니다.", "USER_NOT_FOUND");

    private final String code;
    private final String message;
    private final String status;

    ErrorCode(String code, String message, String status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
