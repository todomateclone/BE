package com.team6.todomateclone.member.controller;

import com.team6.todomateclone.common.response.SuccessResponse;
import com.team6.todomateclone.member.dto.RequestSignupMemberDto;
import com.team6.todomateclone.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class MemberAuthController {

    private final MemberService memberService;
    @PostMapping(value = "/signup", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public SuccessResponse<Object> signup(@RequestPart RequestSignupMemberDto request, @RequestPart MultipartFile multipartFile) throws IOException {
        memberService.signup(request, multipartFile);
        return new SuccessResponse<>("회원가입 성공하였습니다.",null);
    }

}
