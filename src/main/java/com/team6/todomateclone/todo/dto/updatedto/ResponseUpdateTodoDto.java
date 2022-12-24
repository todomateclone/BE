package com.team6.todomateclone.todo.dto.updatedto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponseUpdateTodoDto {
    private Long todoId;
    private String content;
    private Long tagId;
    private Long todoYear;
    private Long todoMonth;
    private Long todoDay;
    private LocalDateTime createdAt;
    private boolean done;

    @Builder
    public ResponseUpdateTodoDto(Long todoId, String content, Long tagId, Long todoYear, Long todoMonth, Long todoDay, LocalDateTime createdAt, boolean done) {
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
