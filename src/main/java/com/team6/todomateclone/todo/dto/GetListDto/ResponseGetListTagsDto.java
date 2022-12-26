package com.team6.todomateclone.todo.dto.GetListDto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseGetListTagsDto {
    private Long tagId;
    private String tagName;
    private String tagColor;

    @Builder
    public ResponseGetListTagsDto(Long tagId, String tagName, String tagColor) {
        this.tagId = tagId;
        this.tagName = tagName;
        this.tagColor = tagColor;
    }
}
