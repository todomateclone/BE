package com.team6.todomateclone.todo.mapper;

import com.team6.todomateclone.todo.dto.createdto.CreateTodoDto;
import com.team6.todomateclone.todo.entity.Todo;
import org.springframework.stereotype.Component;

@Component
public class TodoMapper {

    //ServiceDto -> Entity
    public Todo toEntityTodo(Long tagId, Long memberId, CreateTodoDto createTodoDto){
        return Todo.builder()
                .content(createTodoDto.getContent())
                .todoYear(createTodoDto.getTodoYear())
                .todoMonth(createTodoDto.getTodoMonth())
                .todoDay(createTodoDto.getTodoDay())
                .done(createTodoDto.isDone())
                .tagId(tagId)
                .memberId(memberId)
                .build();
    }

    //Entity -> ServiceDto
    public CreateTodoDto toDtoCreateTodo(Todo todo){
        return CreateTodoDto.builder()
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
