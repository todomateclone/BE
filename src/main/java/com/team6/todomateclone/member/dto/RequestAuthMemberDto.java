package com.team6.todomateclone.member.dto;

import com.team6.todomateclone.common.exception.ValidationGroups;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@ApiModel(value = "회원가입 Request Dto")
@Getter
public class RequestAuthMemberDto {
    @ApiModelProperty(value = "회원 이메일", dataType = "String", example = "test@naver.com")
    @NotBlank(message = "이메일을 입력해주세요.", groups = ValidationGroups.EmailNotEmptyGroup.class)
    @Pattern(regexp = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$", message = "이메일 형식이 올바르지 않습니다.", groups = ValidationGroups.EmailPatternCheckGroup.class)
    private String email;

    @ApiModelProperty(value = "회원 비밀번호", dataType = "String", example = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자")
    @NotBlank(message = "비밀번호를 입력해주세요.", groups = ValidationGroups.PasswordNotEmptyGroup.class)
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.", groups = ValidationGroups.PasswordPatternCheckGroup.class)
    private String password;
}
