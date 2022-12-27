package com.team6.todomateclone.member.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel(value = "프로필 이미지 수정 Response Dto")
@Getter
@Builder
public class ResponseUpdateImageMemberDto {

    @ApiModelProperty(value = "회원 프로필사진", dataType = "String", example = "dahoDSAjkasdk.jpg")
    private String profileImageUrl;
}
