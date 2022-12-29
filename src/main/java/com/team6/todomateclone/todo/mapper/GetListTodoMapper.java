package com.team6.todomateclone.todo.mapper;

import com.team6.todomateclone.member.entity.Member;
import com.team6.todomateclone.tag.entity.Tag;
import com.team6.todomateclone.todo.dto.GetListDto.ResponseGetListTagsDto;
import com.team6.todomateclone.todo.dto.GetListDto.ResponseGetListTodoDto;
import com.team6.todomateclone.todo.dto.GetListDto.ResponseGetListTodosDto;
import com.team6.todomateclone.todo.entity.Todo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetListTodoMapper {
    //Entity -> TagsDto
    public ResponseGetListTagsDto toDtoGetListTags(Tag tags) {
        return ResponseGetListTagsDto.builder()
                .tagId(tags.getTagId())
                .tagName(tags.getTagName())
                .tagColor(tags.getTagColor())
                .build();

    }

    //Entity -> todosDto
    public ResponseGetListTodosDto toDtopGetListTodos(Todo todoDto) {
        return ResponseGetListTodosDto.builder()
                .todoId(todoDto.getTodoId())
                .content(todoDto.getContent())
                .tagId(todoDto.getTagId())
                .todoYear(todoDto.getTodoYear())
                .todoMonth(todoDto.getTodoMonth())
                .todoDay(todoDto.getTodoDay())
                .createdAt(todoDto.getCreatedAt())
                .done(todoDto.isDone())
                .build();
    }

    //Entity -> ResponseListDto
    public ResponseGetListTodoDto toDtopGetListTodo(Member member, Long doneCount, List<ResponseGetListTagsDto> tagsDtos, List<ResponseGetListTodosDto> todosDto) {
        return ResponseGetListTodoDto.builder()
                .nickname(member.getNickname())
                .description(member.getDescription())
                .profileImageUrl(member.getProfileImageUrl())
                .doneCount(doneCount)
                .tags(tagsDtos)
                .todos(todosDto)
                .build();
    }
}
