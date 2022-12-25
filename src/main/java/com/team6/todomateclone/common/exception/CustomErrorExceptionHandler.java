package com.team6.todomateclone.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomErrorExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {CustomErrorException.class})
    protected ResponseEntity<CustomErrorResponse> handleCustomException(CustomErrorException e) {
        return CustomErrorResponse.toResponseEntity(e.getErrorCode());
    }
}