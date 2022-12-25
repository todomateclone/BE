package com.team6.todomateclone.common.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class CustomErrorResponse {
    private final String result;
    private final String msg;
    private final int code;

    /**
     예외 응답 시 코드는 400번으로 통일(팀미팅에서 결정)
    */
    public static ResponseEntity<CustomErrorResponse> toResponseEntity(CustomErrorCodeEnum errorCodeEnum) {
        return ResponseEntity
                .status(400)
                .body(CustomErrorResponse.builder()
                        .result("fail")
                        .msg(errorCodeEnum.getMsg())
                        .code(400)
                        .build()
                );
    }
}
