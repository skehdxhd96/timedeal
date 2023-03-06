package com.example.timedeal.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    DUPLICATED_USERNAME(500, "중복된 아이디 입니다.", "DUPLICATED_USERNAME"),
    USER_NOT_FOUND(500, "유저를 찾을 수 없습니다.", "USER_NOT_FOUND"),
    PRODUCT_NOT_FOUND(500, "존재하지 않는 상품입니다.", "PRODUCT_NOT_FOUND"),
    LOG_IN_FAILURE(500, "로그인에 실패했습니다. 아이디 및 비밀번호를 다시한번 확인해주세요", "LOG_IN_FAILURE"),
    LOG_IN_ESSENTIAL(500, "로그인이 필요합니다.", "LOG_IN_ESSENTIAL"),
    ADMINISTRATOR_ONLY(500, "관리자 기능입니다. 관리자에게 문의해주세요", "ADMINISTRATOR_ONLY");

    private final int code;
    private final String message;
    private final String status;

    ErrorCode(int code, String message, String status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
