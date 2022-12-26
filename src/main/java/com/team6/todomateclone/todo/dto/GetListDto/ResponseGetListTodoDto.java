package com.team6.todomateclone.todo.dto.GetListDto;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ResponseGetListTodoDto {
    private String nickname;
    private String description;
    private String profileImageUrl;
    private Long doneCount;

    private List<ResponseGetListTagsDto> tags = new ArrayList<>();
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
