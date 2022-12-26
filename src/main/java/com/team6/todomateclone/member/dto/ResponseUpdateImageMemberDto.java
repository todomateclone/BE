package com.team6.todomateclone.member.dto;

import com.team6.todomateclone.member.entity.Member;
import lombok.Getter;

@Getter
public class ResponseUpdateImageMemberDto {
    private String profileImageUrl;
    public ResponseUpdateImageMemberDto(Member member){
        this.profileImageUrl=member.getProfileImageUrl();
    }
}
