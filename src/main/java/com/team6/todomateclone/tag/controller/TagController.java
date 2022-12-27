package com.team6.todomateclone.tag.controller;

import com.team6.todomateclone.common.response.SuccessResponse;
import com.team6.todomateclone.common.security.UserDetailsImpl;
import com.team6.todomateclone.tag.dto.RequestTagDto;
import com.team6.todomateclone.tag.dto.ResponseTagDto;
import com.team6.todomateclone.tag.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Api(tags = {"마이페이지 Tag API Controller"})
@RestController
@RequestMapping("/api/mypage/tag")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    // 태그 등록
    @ApiOperation(value = "태그 등록")
    @PostMapping("")
    public SuccessResponse<ResponseTagDto> createTag(@RequestBody RequestTagDto requestTagDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ResponseTagDto response = tagService.createTag(requestTagDto, userDetails.getMember());
        return new SuccessResponse<>("태그 생성 성공" ,response);
    }

    // 태그 조회
    @ApiOperation(value = "태그 조회")
    @GetMapping("")
    public SuccessResponse<List<ResponseTagDto>> getTag(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<ResponseTagDto> response = tagService.getTag(userDetails.getMember().getMemberId());
        return new SuccessResponse<>("태그 조회 성공", response);
    }

    // 태그 수정
    @ApiOperation(value = "태그 수정")
    @PatchMapping("/{tagId}")
    public SuccessResponse<ResponseTagDto> updateTag(@PathVariable Long tagId, @RequestBody RequestTagDto requestTagDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ResponseTagDto response = tagService.updateTag(tagId, requestTagDto, userDetails.getMember());
        return new SuccessResponse<>("태그 수정 성공", response);
    }

    // 태그 삭제
    @ApiOperation(value = "태그 삭제")
    @DeleteMapping("/{tagId}")
    public SuccessResponse<ResponseTagDto> deleteTag(@PathVariable Long tagId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        tagService.deleteTag(tagId, userDetails.getMember());
        return new SuccessResponse<>("태그 삭제 성공", null);
    }
}
