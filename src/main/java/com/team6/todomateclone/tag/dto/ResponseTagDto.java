package com.team6.todomateclone.tag.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseTagDto {

    private Long tagId;
    private String tagName;
    private String tagColor;

}
