package com.example.todayError.exceptionHandler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity handleCustomException(CustomException e) {

        ErrorResponse errorResult = new ErrorResponse();

        errorResult.setHttpStatus(e.getErrorCode().getHttpStatus());
        errorResult.setStatus(e.getErrorCode().getStatus());
        errorResult.setMessage(e.getErrorCode().getMessage());

        return new ResponseEntity(errorResult, errorResult.getHttpStatus());
    }
}