package com.team6.todomateclone.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
public enum CustomErrorCodeEnum {
    /* 투두 예외 메시지 */
<<<<<<< Updated upstream
    TODO_NOT_FOUND("Todo를 찾을 수 없습니다."),
    TODO_INVALID_PERMISSION("Todo 권한이 유효하지 않습니다."),

    /* Tag 예외 메시지 */
    TAG_NOT_FOUND("Tag를 찾을 수 없습니다.");
=======
    MEMBER_NOT_FOUND_MSG("유효하지 않은 회원입니다."),
    TODO_NOT_FOUND_MSG("Todo를 찾을 수 없습니다."),
    TODO_INVALID_PERMISSION_MSG("Todo 권한이 유효하지 않습니다."),

    /* Tag 예외 메시지 */
    TAG_NOT_FOUND_MSG("Tag를 찾을 수 없습니다."),

    /* JWT 예외 메시지 */
    TOKEN_NOT_FOUND_MSG( "토큰이 존재하지 않습니다."),
    INVALID_TOKEN_MSG("토큰이 유효하지 않습니다.");
>>>>>>> Stashed changes

    private final String msg;

    CustomErrorCodeEnum(String msg) {
        this.msg = msg;
    }
}
