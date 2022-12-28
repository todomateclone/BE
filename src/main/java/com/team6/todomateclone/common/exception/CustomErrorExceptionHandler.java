package com.team6.todomateclone.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class CustomErrorExceptionHandler {

    /** Custom 예외 핸들러 **/
    @ExceptionHandler(value = {CustomErrorException.class})
    protected ResponseEntity<CustomErrorResponse> handleCustomException(CustomErrorException e) {
        return CustomErrorResponse.toResponseEntity(e.getErrorCode());
    }

    /** Validation 예외처리 **/
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected ResponseEntity<CustomErrorResponse> handleValidException(MethodArgumentNotValidException e) {
        return CustomErrorResponse.toResponseValidEntity(e.getBindingResult().getFieldError().getDefaultMessage());
    }

    /** todoContorller @Validated @PathVariable 예외처리 **/
    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<CustomErrorResponse> handleCustomValidatedException(ConstraintViolationException e) {
        return CustomErrorResponse.toResponseValidEntity(e.getConstraintViolations().iterator().next().getMessage());
    }

    /** 지원하지 않는 HTTP 요청 예외처리 **/
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<CustomErrorResponse> handleCustomHttpRequestMethodNotSupportException() {
        return CustomErrorResponse.toResponseValidEntity("비정상적인 요청입니다.");
    }

    /** 그외 나머지 예외 처리 **/
    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<CustomErrorResponse> handleCustomGlobalException(Exception e) {
        log.info(e.getMessage()); //예외 로그
        return CustomErrorResponse.toResponseValidEntity("비정상적인 요청입니다.");
    }
}