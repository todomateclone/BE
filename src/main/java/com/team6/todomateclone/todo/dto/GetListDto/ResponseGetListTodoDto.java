package com.team6.todomateclone.todo.dto.GetListDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "투두 전체 조회(월단위) Response Dto")
@Getter
public class ResponseGetListTodoDto {
    @ApiModelProperty(value = "회원 닉네임", dataType = "String", example = "미라클모뉭")
    private String nickname;
    @ApiModelProperty(value = "회원 소개", dataType = "String", example = "만나서 반갑습니다.")
    private String description;
    @ApiModelProperty(value = "회원 프로필사진", dataType = "String", example = "dahoDSAjkasdk.jpg")
    private String profileImageUrl;
    @ApiModelProperty(value = "목표 달성 수", dataType = "Long", example = "3")
    private Long doneCount;
    @ApiModelProperty(value = "태그 목록", dataType = "List", example = "")
    private List<ResponseGetListTagsDto> tags = new ArrayList<>();
    @ApiModelProperty(value = "투두 목록", dataType = "List", example = "")
    private List<ResponseGetListTodosDto> todos = new ArrayList<>();

    @Builder
    public ResponseGetListTodoDto(String nickname, String description, String profileImageUrl, Long doneCount, List<ResponseGetListTagsDto> tags, List<ResponseGetListTodosDto> todos) {
        this.nickname = nickname;
        this.description = description;
        this.profileImageUrl = profileImageUrl;
        this.doneCount = doneCount;
        this.tags = tags;
        this.todos = todos;
    }
}
