package com.team6.todomateclone.member.mapper;

import com.team6.todomateclone.member.dto.ResponseInfoMemberDto;
import com.team6.todomateclone.member.dto.ResponseUpdateImageMemberDto;
import com.team6.todomateclone.member.dto.ResponseUpdateInfoMemberDto;
import com.team6.todomateclone.member.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {
    public Member toEntity(String email, String password, String profileImageUrl){
        return Member.builder()
                .email(email)
                .password(password)
                .nickname("me")
                .description("")
                .profileImageUrl(profileImageUrl)
                .build();
    }
    public ResponseInfoMemberDto toResponseMemberDtoInfo(Member member){
        return ResponseInfoMemberDto.builder()
                .nickname(member.getNickname())
                .description(member.getDescription())
                .profileImageUrl(member.getProfileImageUrl())
                .build();
    }

    public ResponseUpdateImageMemberDto toResponseMemberDtoImage(Member member){
        return ResponseUpdateImageMemberDto.builder()
                .profileImageUrl(member.getProfileImageUrl())
                .build();
    }

    public ResponseUpdateInfoMemberDto toResponseMemberDtoUpdateInfo(Member member){
        return ResponseUpdateInfoMemberDto.builder()
                .nickname(member.getNickname())
                .description(member.getDescription())
                .build();
    }
}
