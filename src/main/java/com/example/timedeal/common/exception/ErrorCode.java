package com.example.timedeal.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    DUPLICATED_USERNAME(500, "중복된 아이디 입니다.", "DUPLICATED_USERNAME"),
    USER_NOT_FOUND(500, "존재하지 않는 유저입니다.", "USER_NOT_FOUND"),
    PRODUCT_NOT_FOUND(500, "존재하지 않는 상품입니다.", "PRODUCT_NOT_FOUND"),
    EVENT_NOT_FOUND(500, "존재하지 않는 이벤트입니다.", "EVENT_NOT_FOUND"),
    LOG_IN_FAILURE(500, "로그인에 실패했습니다. 아이디 및 비밀번호를 다시한번 확인해주세요", "LOG_IN_FAILURE"),
    LOG_IN_ESSENTIAL(500, "로그인이 필요합니다.", "LOG_IN_ESSENTIAL"),
    ALREADY_PUBLISEHD(500, "이미 발행된 이벤트입니다.", "ALREADY_PUBLISHED"),
    ALREADY_HAS_EVENT(500, "이미 이벤트에 등록된 상품입니다. 먼저 등록된 이벤트를 해제해주세요", "ALREADY_HAS_EVENT"),
    EVENT_NOT_MATCHING(500, "해당 이벤트 상품이 존재하지 않습니다.", "EVENT_NOT_MATCHING"),
    PUBLISH_NOT_YET(500, "이벤트 발행이 필요합니다.", "PUBLISH_NOT_YET"),
    NOT_IN_PROGRESSING(500, "이벤트 진행 기간이 아닙니다.", "NOT_IN_PROGRESSING"),
    NOT_EVENT_PRODUCT(500, "이벤트 상품이 아닙니다.", "NOT_EVENT_PRODUCT"),
    NOT_ENOUGH_STOCK(500, "재고가 부족합니다.", "NOT_ENOUGH_STOCK"),
    ADMINISTRATOR_ONLY(500, "관리자 기능입니다. 관리자에게 문의해주세요", "ADMINISTRATOR_ONLY"),
    ALREADY_IN_ORDER(500, "이미 주문목록에 있습니다.", "ALREADY_IN_ORDER"),
    NOT_IN_ORDER(500, "주문목록에 존재하지 않는 상품입니다.", "NOT_IN_ORDER"),
    STOCK_NOT_ENOUGH(500, "재고가 부족합니다.", "STOCK_NOT_ENOUGH"),
    ORDER_NOT_FOUND(500, "주문이 존재하지 않습니다.", "ORDER_NOT_FOUND"),
    CANNOT_GET_LOCK(500, "주문이 너무 많습니다. 잠시 후 다시 시도해 주세요", "CANNOT_GET_LOCK");

    private final int code;
    private final String message;
    private final String status;

    ErrorCode(int code, String message, String status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
