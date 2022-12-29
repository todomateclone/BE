package com.team6.todomateclone.member.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel(value = "회원 소개 조회 Response Dto")
@Getter
@Builder
public class ResponseInfoMemberDto {
    @ApiModelProperty(value = "회원 닉네임", dataType = "String", example = "미라클모뉭")
    private String nickname;
    @ApiModelProperty(value = "회원 소개", dataType = "String", example = "만나서 반갑습니다.")
    private String description;
    @ApiModelProperty(value = "회원 프로필사진", dataType = "String", example = "dahoDSAjkasdk.jpg")
    private String profileImageUrl;
}
