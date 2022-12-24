package com.team6.todomateclone.todo.dto.createdto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateTodoDto {
    private Long tagId;
    private Long memberId;
    private Long todoId;
    private String content;
    private Long todoYear;
    private Long todoMonth;
    private Long todoDay;
    private boolean done;

    private LocalDateTime createdAt;

    @Builder
    public CreateTodoDto(Long tagId, Long memberId, Long todoId, String content, Long todoYear, Long todoMonth, Long todoDay, LocalDateTime createdAt, boolean done) {
        this.tagId = tagId;
        this.memberId = memberId;
        this.todoId = todoId;
        this.content = content;
        this.todoYear = todoYear;
        this.todoMonth = todoMonth;
        this.todoDay = todoDay;
        this.createdAt = createdAt;
        this.done = done;
    }

    public ResponseCreateTodoDto toResponseCreateTodoDto() {
        return ResponseCreateTodoDto.builder()
                .todoId(todoId)
                .content(content)
                .tagId(tagId)
                .todoYear(todoYear)
                .todoMonth(todoMonth)
                .todoDay(todoDay)
                .createdAt(createdAt)
                .done(isDone())
                .build();
    }
}
