package com.team6.todomateclone.tag.controller;

import com.team6.todomateclone.common.response.SuccessResponse;
import com.team6.todomateclone.tag.dto.RequestTagDto;
import com.team6.todomateclone.tag.dto.ResponseTagDto;
import com.team6.todomateclone.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tag")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    // 태그 등록
    @PostMapping("")
    public SuccessResponse<List<ResponseTagDto>> createTag(@RequestBody RequestTagDto requestTagDto) {
        List<ResponseTagDto> response = tagService.createTag(requestTagDto);
        return new SuccessResponse<>("태그 생성 성공" ,response);
    }

    // 태그 수정
    @PatchMapping("/{tagId}")
    public SuccessResponse<ResponseTagDto> updateTag(@PathVariable Long tagId, @RequestBody RequestTagDto requestTagDto) {
        ResponseTagDto response = tagService.updateTag(tagId, requestTagDto);
        return new SuccessResponse<>("태그 수정 성공", response);
    }

    // 태그 삭제
    @DeleteMapping("/{tagId}")
    public SuccessResponse<ResponseTagDto> deleteTag(@PathVariable Long tagId) {
        tagService.deleteTag(tagId);
        return new SuccessResponse<>("태그 삭제 성공", null);
    }


}
