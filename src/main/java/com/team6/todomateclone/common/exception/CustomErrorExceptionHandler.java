package com.team6.todomateclone.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class CustomErrorExceptionHandler {

    @ExceptionHandler(value = {CustomErrorException.class})
    protected ResponseEntity<CustomErrorResponse> handleCustomException(CustomErrorException e) {
        return CustomErrorResponse.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected ResponseEntity<CustomErrorResponse> handleValidException(MethodArgumentNotValidException e) {
        return CustomErrorResponse.toResponseValidEntity(e.getBindingResult().getFieldError().getDefaultMessage());
    }

    /** todoContorller @Validated @PathVariable 예외처리 **/
    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<CustomErrorResponse> handleCustomValidatedException(ConstraintViolationException e) {
        return CustomErrorResponse.toResponseValidEntity(e.getConstraintViolations().iterator().next().getMessage());
    }

}