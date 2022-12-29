package com.team6.todomateclone.tag.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel(value = "태그 Request Dto")
@Getter
public class RequestTagDto {
    @ApiModelProperty(value = "태그 이름", dataType = "String", example = "다이어트")
    private String tagName;
    @ApiModelProperty(value = "태그 색상", dataType = "String", example = "#000000")
    private String tagColor;
}
