package com.example.timedeal.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.UnexpectedTypeException;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    private final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorCode> BusinessExceptionHandler(BusinessException e) {

        ErrorCode errorCode = e.getErrorCode();

        logger.warn(errorCode.getMessage());

        return ResponseEntity
                .status(errorCode.getCode())
                .body(errorCode);
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<GlobalExceptionResponse> UnexpectedTypeExceptionHandler(UnexpectedTypeException e) {

        logger.warn(e.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new GlobalExceptionResponse(e.getCause(), e.getMessage()));
    }

    // TODO : valid / user-admin / badrequest / NotFound / RunTime
}
