package com.team6.todomateclone.common.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private final String result;
    private final String msg;

    public ErrorResponse(String msg){
        this.result = "fail";
        this.msg = msg;
    }

}
