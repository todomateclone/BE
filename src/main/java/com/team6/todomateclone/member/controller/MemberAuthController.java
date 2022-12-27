package com.team6.todomateclone.member.controller;

import com.team6.todomateclone.common.response.SuccessResponse;
import com.team6.todomateclone.member.dto.RequestLoginMemberDto;
import com.team6.todomateclone.member.dto.RequestSignupMemberDto;
import com.team6.todomateclone.member.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Api(tags = {"회원페이지 API Controller"})
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class MemberAuthController {

    private final MemberService memberService;

    @ApiOperation(value = "회원가입")
    @PostMapping("/signup")
    public SuccessResponse<Object> signup(@Valid @RequestBody RequestSignupMemberDto request) {
        memberService.signup(request);
        return new SuccessResponse<>("회원가입 성공하였습니다.", null);
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public SuccessResponse<Object> login(@RequestBody RequestLoginMemberDto request, HttpServletResponse response) {
        memberService.login(request, response);
        return new SuccessResponse<>("로그인 성공하였습니다.", null);
    }

}
