package com.team6.todomateclone.tag.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel(value = "태그 Response Dto")
@Getter
@Builder
public class ResponseTagDto {
    @ApiModelProperty(value = "태그 아이디", dataType = "Long", example = "1")
    private Long tagId;
    @ApiModelProperty(value = "태그 이름", dataType = "String", example = "다이어트")
    private String tagName;
    @ApiModelProperty(value = "태그 색상", dataType = "String", example = "#000000")
    private String tagColor;
}
