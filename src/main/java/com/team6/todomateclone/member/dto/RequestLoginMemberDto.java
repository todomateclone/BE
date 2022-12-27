package com.team6.todomateclone.member.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel(value = "로그인 Request Dto")
@Getter
public class RequestLoginMemberDto {
    @ApiModelProperty(value = "회원 이메일", dataType = "String", example = "test@naver.com")
    private String email;
    @ApiModelProperty(value = "회원 비밀번호", dataType = "String", example = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자")
    private String password;
}
