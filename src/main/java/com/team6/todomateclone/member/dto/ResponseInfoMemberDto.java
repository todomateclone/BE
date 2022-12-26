package com.team6.todomateclone.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseInfoMemberDto {
    private String nickname;
    private String description;
    private String profileImageUrl;
}
