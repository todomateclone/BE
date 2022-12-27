package com.team6.todomateclone.todo.mapper;

import com.team6.todomateclone.todo.dto.updatedto.UpdateTodoDto;
import com.team6.todomateclone.todo.entity.Todo;
import org.springframework.stereotype.Component;

@Component
public class UpdateTodoMapper {
    //ServiceDto -> Entity
    public Todo toEntityTodo(UpdateTodoDto updateTodoDto){
        return Todo.builder()
                .todoId(updateTodoDto.getTodoId())
                .content(updateTodoDto.getContent())
                .todoYear(updateTodoDto.getTodoYear())
                .todoMonth(updateTodoDto.getTodoMonth())
                .todoDay(updateTodoDto.getTodoDay())
                .done(updateTodoDto.isDone())
                .tagId(updateTodoDto.getTagId())
                .memberId(updateTodoDto.getMemberId())
                .build();
    }

    //Entity -> ServiceDto
    public UpdateTodoDto toDtoUpdateTodo(Todo todo){
        return UpdateTodoDto.builder()
                .tagId(todo.getTagId())
                .memberId(todo.getMemberId())
                .todoId(todo.getTodoId())
                .content(todo.getContent())
                .todoYear(todo.getTodoYear())
                .todoMonth(todo.getTodoMonth())
                .todoDay(todo.getTodoDay())
                .createdAt(todo.getCreatedAt())
                .done(todo.isDone())
                .build();
    }

}
