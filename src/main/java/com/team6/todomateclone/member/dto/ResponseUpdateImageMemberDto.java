package com.team6.todomateclone.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseUpdateImageMemberDto {
    private String profileImageUrl;
}
