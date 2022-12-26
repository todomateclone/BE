package com.team6.todomateclone.member.controller;

import com.team6.todomateclone.common.response.SuccessResponse;
import com.team6.todomateclone.member.dto.RequestUpdateInfoMemberDto;
import com.team6.todomateclone.member.dto.ResponseInfoMemberDto;
import com.team6.todomateclone.member.dto.ResponseUpdateImageMemberDto;
import com.team6.todomateclone.member.dto.ResponseUpdateInfoMemberDto;
import com.team6.todomateclone.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberInfoController {
    private final MemberService memberService;
    @GetMapping("/{memberId}")
    SuccessResponse<ResponseInfoMemberDto> getMemberInfo(@PathVariable Long memberId){
        ResponseInfoMemberDto response = memberService.getMemberInfo(memberId);
        return new SuccessResponse<>("회원정보 조회 성공하였습니다.",response);
    }

    @PatchMapping("/{memberId}")
    public SuccessResponse<ResponseUpdateInfoMemberDto> updateInfo(@PathVariable Long memberId, @RequestBody RequestUpdateInfoMemberDto request){
        ResponseUpdateInfoMemberDto response = memberService.updateInfo(memberId,request);
        return new SuccessResponse<>("회원정보 수정 성공하였습니다.",response);
    }

    @PatchMapping("/pimage/{memberId}")
    public SuccessResponse<ResponseUpdateImageMemberDto> updateImage(@PathVariable Long memberId, MultipartFile multipartFile) throws IOException {
        ResponseUpdateImageMemberDto response = memberService.updateImage(memberId, multipartFile);
        return new SuccessResponse<>("프로필 이미지 변경 성공하였습니다.",response);
    }

    /* 기본 이미지로 변경 */
    @PatchMapping("/dimage/{memberId}")
    public SuccessResponse<ResponseUpdateImageMemberDto> updateToDefaultImage(@PathVariable Long memberId) {
        ResponseUpdateImageMemberDto response = memberService.updateToDefaultImage(memberId);
        return new SuccessResponse<>("기본 이미지로 변경 성공하였습니다.",response);
    }

}
