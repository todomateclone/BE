package com.team6.todomateclone.member.dto;

import com.team6.todomateclone.member.entity.Member;
import lombok.Getter;

@Getter
public class ResponseUpdateInfoMemberDto {
    private String nickname;
    private String description;

    public ResponseUpdateInfoMemberDto(Member member){
        this.nickname=member.getNickname();
        this.description=member.getDescription();
    }
}
