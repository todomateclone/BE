package com.team6.todomateclone.member.dto;

import com.team6.todomateclone.member.entity.Member;
import lombok.Getter;

@Getter
public class ResponseInfoMemberDto {
    private String nickname;
    private String description;
    private String profileImageUrl;

    public ResponseInfoMemberDto(Member member){
        this.nickname = member.getNickname();
        this.description=member.getDescription();
        this.profileImageUrl=member.getProfileImageUrl();
    }
}
