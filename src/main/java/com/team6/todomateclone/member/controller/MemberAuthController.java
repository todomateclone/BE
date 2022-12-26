package com.team6.todomateclone.member.controller;

import com.team6.todomateclone.common.response.SuccessResponse;
import com.team6.todomateclone.member.dto.RequestSignupMemberDto;
import com.team6.todomateclone.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class MemberAuthController {

    private final MemberService memberService;
    @PostMapping("/signup")
    public SuccessResponse<Object> signup(@RequestBody RequestSignupMemberDto request){
        memberService.signup(request);
        return new SuccessResponse<>("회원가입 성공하였습니다.",null);

    }

}
