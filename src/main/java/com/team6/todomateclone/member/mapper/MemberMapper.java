package com.team6.todomateclone.member.mapper;

import com.team6.todomateclone.member.dto.RequestSignupMemberDto;
import com.team6.todomateclone.member.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {
    public Member toEntity(RequestSignupMemberDto request, String profileImageUrl){
        return Member.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .nickname(request.getNickname())
                .description(request.getDescription())
                .profileImageUrl(profileImageUrl)
                .build();
    }



}
