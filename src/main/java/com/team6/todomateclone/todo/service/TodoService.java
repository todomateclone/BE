package com.team6.todomateclone.todo.service;

import com.team6.todomateclone.common.exception.CustomErrorCodeEnum;
import com.team6.todomateclone.common.exception.CustomErrorException;
import com.team6.todomateclone.tag.repository.TagRepository;
import com.team6.todomateclone.todo.dto.createdto.CreateTodoDto;
import com.team6.todomateclone.todo.dto.createdto.ResponseCreateTodoDto;
import com.team6.todomateclone.todo.dto.updatedto.ResponseUpdateTodoDto;
import com.team6.todomateclone.todo.dto.updatedto.UpdateTodoDto;
import com.team6.todomateclone.todo.entity.Todo;
import com.team6.todomateclone.todo.mapper.CreateTodoMapper;
import com.team6.todomateclone.todo.mapper.UpdateTodoMapper;
import com.team6.todomateclone.todo.repository.TodoRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TodoService {

    /** Repository **/
    private final TagRepository tagRepository;
    private final TodoRespository todoRespository;

    /** Mapper **/
    private final CreateTodoMapper createTodoMapper;
    private final UpdateTodoMapper updateTodoMapper;


    /** 2. 투두 등록 **/
    @Transactional
    public ResponseCreateTodoDto createTodo(Long tagId, Long memberId, CreateTodoDto createTodoDto) {
        //2-1. 태그 유효성 검사
        boolean isTagId = tagRepository.existsById(tagId);

        if (!isTagId) { //태그가 없다면 에러 반환
            throw new CustomErrorException(CustomErrorCodeEnum.TAG_NOT_FOUND_MSG);
        }

        //2-2. ServiceDto -> Entity
        Todo todo = createTodoMapper.toEntityTodo(tagId, memberId, createTodoDto);

        //2-3. 투두 등록
        todo = todoRespository.save(todo);

        //2-4. Entity -> ServiceDto
        createTodoDto = createTodoMapper.toDtoCreateTodo(todo);

        //2-5. ServiceDto -> ResponseDto
        ResponseCreateTodoDto responseCreateTodoDto = createTodoDto.toResponseCreateTodoDto();

        //2-6. 결과 반환
        return responseCreateTodoDto;
    }

    /** 3. 투두 수정 **/
    @Transactional
    public ResponseUpdateTodoDto updateTodo(Long todoId, Long memberId, UpdateTodoDto updateTodoDto) {
        //3-1. 투두 유효성 검사
        Todo todo = todoRespository.findById(todoId).orElseThrow(
                () -> new CustomErrorException(CustomErrorCodeEnum.TODO_NOT_FOUND_MSG)
        );

        //3-2. 투두 수정 권한 여부 검사
        if (!memberId.equals(todo.getMemberId())){
            throw new CustomErrorException(CustomErrorCodeEnum.TODO_INVALID_PERMISSION_MSG);
        }

        //3-3. ServiceDto -> Entity
        Todo updateTodo = updateTodoMapper.toEntityTodo(updateTodoDto);

        //3-4. 투두 수정
        todo.updateTodo(updateTodo);

        //3-5. Entity -> ServiceDto
        updateTodoDto = updateTodoMapper.toDtoUpdateTodo(todo);

        //3-6. ServiceDto -> ResponseDto
        ResponseUpdateTodoDto responseUpdateTodoDto = updateTodoDto.toResponseUpdateTodoDto();

        //3-7. 결과 반환
        return responseUpdateTodoDto;
    }

    /** 4. 투두 삭제 **/
    @Transactional
    public void deleteTodo(Long todoId, Long memberId) {
        //4-1. 투두 유효성 검사
        Todo todo = todoRespository.findById(todoId).orElseThrow(
                () -> new CustomErrorException(CustomErrorCodeEnum.TODO_NOT_FOUND_MSG)
        );

        //4-2. 투두 삭제 권한 여부 검사
        if (!memberId.equals(todo.getMemberId())){
            throw new CustomErrorException(CustomErrorCodeEnum.TODO_INVALID_PERMISSION_MSG);
        }

        //4-3. 투두 삭제
        todoRespository.deleteById(todo.getTodoId());
    }
}
