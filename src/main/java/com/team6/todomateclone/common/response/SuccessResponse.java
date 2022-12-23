package com.team6.todomateclone.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessResponse<T> {
    private final String result;
    private final String msg;
    private final T data;

    public SuccessResponse(String msg, T data){
        this.result = "success";
        this.msg = msg;
        this.data = data;
    }

}
