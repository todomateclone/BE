package com.team6.todomateclone.todo.dto.GetListDto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class ResponseGetListTodosDto {
    private Long todoId;
    private String content;
    private Long tagId;
    private Long todoYear;
    private Long todoMonth;
    private Long todoDay;
    private LocalDateTime createdAt;
    private boolean done;

    @Builder
    public ResponseGetListTodosDto(Long todoId, String content, Long tagId, Long todoYear, Long todoMonth, Long todoDay, LocalDateTime createdAt, boolean done) {
        this.todoId = todoId;
        this.content = content;
        this.tagId = tagId;
        this.todoYear = todoYear;
        this.todoMonth = todoMonth;
        this.todoDay = todoDay;
        this.createdAt = createdAt;
        this.done = done;
    }
}
