package com.team6.todomateclone.todo.dto.createdto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@ApiModel(value = "투두 Response Dto")
@Getter
public class ResponseCreateTodoDto {
    @ApiModelProperty(value = "투두 아이디", dataType = "Long", example = "1")
    private Long todoId;
    @ApiModelProperty(value = "투두 내용", dataType = "String", example = "프로틴 구입")
    private String content;
    @ApiModelProperty(value = "태그 아이디", dataType = "Long", example = "1")
    private Long tagId;
    @ApiModelProperty(value = "투두 등록연도", dataType = "Long", example = "2022")
    private Long todoYear;
    @ApiModelProperty(value = "투두 등록월", dataType = "Long", example = "12")
    private Long todoMonth;
    @ApiModelProperty(value = "투두 등록일", dataType = "Long", example = "27")
    private Long todoDay;
    @ApiModelProperty(value = "투두 생성일자", dataType = "LocalDateTime", example = "2022-12-27T11:55:30")
    private LocalDateTime createdAt;
    @ApiModelProperty(value = "투두 달성", dataType = "boolean", example = "true")
    private boolean done;

    @Builder
    public ResponseCreateTodoDto(Long todoId, String content, Long tagId, Long todoYear, Long todoMonth, Long todoDay, LocalDateTime createdAt, boolean done) {
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
