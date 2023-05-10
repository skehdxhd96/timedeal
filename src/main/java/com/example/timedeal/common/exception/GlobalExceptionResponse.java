package com.example.timedeal.common.exception;

import lombok.Getter;

@Getter
public class GlobalExceptionResponse {

    public Throwable cause;
    public String message;

    public GlobalExceptionResponse(Throwable cause, String message) {
        this.cause = cause;
        this.message = message;
    }
}
