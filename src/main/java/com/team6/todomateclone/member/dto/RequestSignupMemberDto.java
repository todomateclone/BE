package com.team6.todomateclone.member.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@ApiModel(value = "회원가입 Request Dto")
@Getter
public class RequestSignupMemberDto {
    @ApiModelProperty(value = "회원 이메일", dataType = "String", example = "test@naver.com")
    @NotBlank
    @Pattern(regexp = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$", message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @ApiModelProperty(value = "회원 비밀번호", dataType = "String", example = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자")
    @NotBlank
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;
}
