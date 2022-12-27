package com.team6.todomateclone.member.controller;

import com.team6.todomateclone.common.response.SuccessResponse;
import com.team6.todomateclone.member.dto.RequestAuthMemberDto;
import com.team6.todomateclone.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class MemberAuthController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public SuccessResponse<Object> signup(@Valid @RequestBody RequestAuthMemberDto request, HttpServletResponse response){
        memberService.signup(request);
        memberService.login(request, response);
        return new SuccessResponse<>("회원가입 성공하였습니다.",null);
    }

    @PostMapping("/login")
    public SuccessResponse<Object> login(@RequestBody RequestAuthMemberDto request, HttpServletResponse response) {
        memberService.login(request, response);
        return new SuccessResponse<>("로그인 성공하였습니다.", null);
    }

}
