package com.team6.todomateclone.todo.service;

import com.team6.todomateclone.tag.repository.TagRepository;
import com.team6.todomateclone.todo.dto.createdto.CreateTodoDto;
import com.team6.todomateclone.todo.dto.createdto.ResponseCreateTodoDto;
import com.team6.todomateclone.todo.entity.Todo;
import com.team6.todomateclone.todo.mapper.TodoMapper;
import com.team6.todomateclone.todo.repository.TodoRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TagRepository tagRepository;
    private final TodoRespository todoRespository;
    private final TodoMapper todoMapper;

    public ResponseCreateTodoDto createTodo(Long tagId, Long memberId, CreateTodoDto createTodoDto) {
        //1. 태그 유효성 검사
        boolean isTagId = tagRepository.existsById(tagId);

        if (!isTagId) { //태그가 없다면 에러 반환
            System.out.println("Error : 태그가 없습니다.");
        }

        //2. ServiceDto -> Entity
        Todo todo = todoMapper.toEntityTodo(tagId, memberId, createTodoDto);

        //3. 투두 등록
        todo = todoRespository.save(todo);

        //4. Entitu -> ServiceDto
        createTodoDto = todoMapper.toDtoCreateTodo(todo);

        //5. ServiceDto -> ResponseDto
        ResponseCreateTodoDto responseCreateTodoDto = createTodoDto.toResponseCreateTodoDto();

        //6. 결과 반환
        return responseCreateTodoDto;
    }
}
