package com.team6.todomateclone.member.dto;

import com.team6.todomateclone.member.entity.Member;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
@ApiModel(value = "회원 소개 조회 Response Dto")
@Getter
public class ResponseInfoMemberDto {
    @ApiModelProperty(value = "회원 닉네임", dataType = "String", example = "미라클모뉭")
    private String nickname;
    @ApiModelProperty(value = "회원 소개", dataType = "String", example = "만나서 반갑습니다.")
    private String description;
    @ApiModelProperty(value = "회원 프로필사진", dataType = "String", example = "dahoDSAjkasdk.jpg")
    private String profileImageUrl;

    public ResponseInfoMemberDto(Member member){
        this.nickname = member.getNickname();
        this.description=member.getDescription();
        this.profileImageUrl=member.getProfileImageUrl();
    }
}
