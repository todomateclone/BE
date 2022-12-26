package com.team6.todomateclone.member.mapper;

import com.team6.todomateclone.member.dto.RequestSignupMemberDto;
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

}
