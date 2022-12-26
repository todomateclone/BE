package com.team6.todomateclone.common.exception;

import lombok.Getter;

@Getter
public enum CustomErrorCodeEnum {
    /* 투두 예외 메시지 */
    MEMBER_NOT_FOUND_MSG("유효하지 않은 회원입니다."),
    TODO_NOT_FOUND("Todo를 찾을 수 없습니다."),
    TODO_INVALID_PERMISSION("Todo 권한이 유효하지 않습니다."),

    /* Tag 예외 메시지 */
    TAG_NOT_FOUND("Tag를 찾을 수 없습니다.");

    private final String msg;

    CustomErrorCodeEnum(String msg) {
        this.msg = msg;
    }
}
