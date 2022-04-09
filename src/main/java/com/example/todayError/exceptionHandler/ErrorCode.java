package com.example.todayError.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    DUPLICATE_NICKNAME(HttpStatus.CONFLICT,"409","중복된 닉네임입니다");

    private final HttpStatus httpStatus;
    private final String status;
    private final String message;
}