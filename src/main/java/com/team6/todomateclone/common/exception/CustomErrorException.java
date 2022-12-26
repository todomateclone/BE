package com.team6.todomateclone.common.exception;

import lombok.Getter;

@Getter
public class CustomErrorException extends RuntimeException{
    private final CustomErrorCodeEnum errorCode;

    public CustomErrorException(CustomErrorCodeEnum errorCode) {
        this.errorCode = errorCode;
    }
}
