package com.team6.todomateclone.tag.controller;

import com.team6.todomateclone.common.response.SuccessResponse;
import com.team6.todomateclone.common.security.UserDetailsImpl;
import com.team6.todomateclone.tag.dto.RequestTagDto;
import com.team6.todomateclone.tag.dto.ResponseTagDto;
import com.team6.todomateclone.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mypage/tag")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    // 태그 등록
    @PostMapping("")
    public SuccessResponse<ResponseTagDto> createTag(@RequestBody RequestTagDto requestTagDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ResponseTagDto response = tagService.createTag(requestTagDto, userDetails.getMember());
        return new SuccessResponse<>("태그 생성 성공" ,response);
    }

    // 태그 조회
    @GetMapping("")
    public SuccessResponse<List<ResponseTagDto>> getTag(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<ResponseTagDto> response = tagService.getTag(userDetails.getMember().getMemberId());
        return new SuccessResponse<>("태그 조회 성공", response);
    }

    // 태그 수정
    @PatchMapping("/{tagId}")
    public SuccessResponse<ResponseTagDto> updateTag(@PathVariable Long tagId, @RequestBody RequestTagDto requestTagDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ResponseTagDto response = tagService.updateTag(tagId, requestTagDto, userDetails.getMember());
        return new SuccessResponse<>("태그 수정 성공", response);
    }

    // 태그 삭제
    @DeleteMapping("/{tagId}")
    public SuccessResponse<ResponseTagDto> deleteTag(@PathVariable Long tagId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        tagService.deleteTag(tagId, userDetails.getMember());
        return new SuccessResponse<>("태그 삭제 성공", null);
    }


}
