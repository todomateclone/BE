package com.team6.todomateclone.member.controller;

import com.team6.todomateclone.common.response.SuccessResponse;
import com.team6.todomateclone.common.security.UserDetailsImpl;
import com.team6.todomateclone.member.dto.RequestUpdateInfoMemberDto;
import com.team6.todomateclone.member.dto.ResponseInfoMemberDto;
import com.team6.todomateclone.member.dto.ResponseUpdateImageMemberDto;
import com.team6.todomateclone.member.dto.ResponseUpdateInfoMemberDto;
import com.team6.todomateclone.member.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(tags = {"회원설정페이지 API Controller"})
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberInfoController {
    private final MemberService memberService;
    @ApiOperation(value = "회원 소개 조회")
    @GetMapping
    SuccessResponse<ResponseInfoMemberDto> getMemberInfo(@AuthenticationPrincipal UserDetailsImpl userDetails){
        Long memberId = userDetails.getMember().getMemberId();
        ResponseInfoMemberDto response = memberService.getMemberInfo(memberId);
        return new SuccessResponse<>("회원정보 조회 성공하였습니다.",response);
    }

    @ApiOperation(value = "회원 소개 수정")
    @PatchMapping
    public SuccessResponse<ResponseUpdateInfoMemberDto> updateInfo(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody RequestUpdateInfoMemberDto request){
        Long memberId = userDetails.getMember().getMemberId();
        ResponseUpdateInfoMemberDto response = memberService.updateInfo(memberId,request);
        return new SuccessResponse<>("회원정보 수정 성공하였습니다.",response);
    }

    @ApiOperation(value = "프로필 이미지 변경")
    @PatchMapping("/pimage")
    public SuccessResponse<ResponseUpdateImageMemberDto> updateImage(@AuthenticationPrincipal UserDetailsImpl userDetails, MultipartFile multipartFile) throws IOException {
        Long memberId = userDetails.getMember().getMemberId();
        ResponseUpdateImageMemberDto response = memberService.updateImage(memberId, multipartFile);
        return new SuccessResponse<>("프로필 이미지 변경 성공하였습니다.",response);
    }

    /* 기본 이미지로 변경 */
    @ApiOperation(value = "프로필 이미지 초기화")
    @PatchMapping("/dimage")
    public SuccessResponse<ResponseUpdateImageMemberDto> updateToDefaultImage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long memberId = userDetails.getMember().getMemberId();
        ResponseUpdateImageMemberDto response = memberService.updateToDefaultImage(memberId);
        return new SuccessResponse<>("기본 이미지로 변경 성공하였습니다.",response);
    }
}