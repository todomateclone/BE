package com.team6.todomateclone.todo.controller;

import com.team6.todomateclone.common.response.SuccessResponse;
import com.team6.todomateclone.todo.dto.createdto.CreateTodoDto;
import com.team6.todomateclone.todo.dto.createdto.RequestCreateTodoDto;
import com.team6.todomateclone.todo.dto.createdto.ResponseCreateTodoDto;
import com.team6.todomateclone.todo.dto.updatedto.RequestUpdateTodoDto;
import com.team6.todomateclone.todo.dto.updatedto.ResponseUpdateTodoDto;
import com.team6.todomateclone.todo.dto.updatedto.UpdateTodoDto;
import com.team6.todomateclone.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    //1. 투투 전체조회(월단위)
    @GetMapping("/{todoYear}/{todoMonth}")
    public SuccessResponse<Object> getTodos(@PathVariable Long todoYear, @PathVariable Long todoMonth){ //UserDetails 필요
        System.out.println("getTodos : " + todoYear + " / " + todoMonth);
        return new SuccessResponse("전체조회", null);
    }

    //2. 투두 등록
    @PostMapping("/{tagId}")
    public SuccessResponse<ResponseCreateTodoDto> createTodo(@PathVariable Long tagId, @RequestBody @Valid RequestCreateTodoDto requestCreateTodoDto){//UserDetails 필요
        //2-1. RequestDto -> ServiceDto
        CreateTodoDto createTodoDto = requestCreateTodoDto.toCreateTodoDto();

        //2-2. 투두 등록 서비스 진행
        ResponseCreateTodoDto data = todoService.createTodo(tagId, 1L, createTodoDto); //userDetails 대신 사용

        //2-3. 결과 반환
        return new SuccessResponse<>("일정 등록 성공하였습니다.", data);
    }

    //3. 투두 수정
    @PutMapping("/{todoId}")
    public SuccessResponse<ResponseUpdateTodoDto> updateTodo(@PathVariable Long todoId, @RequestBody @Valid RequestUpdateTodoDto requestUpdateTodoDto){//UserDetails 필요
        //3-1. RequestDto -> ServiceDto
        UpdateTodoDto updateTodoDto = requestUpdateTodoDto.toUpdateTodoDto();

        //3-2. 투두 수정 서비스 진행
        ResponseUpdateTodoDto data = todoService.updateTodo(todoId, 1L, updateTodoDto);

        //3-3. 결과 반환
        return new SuccessResponse<>("일정 수정 성공하였습니다.", data);
    }

    //4. 투두 삭제
    @DeleteMapping("/{todoId}")
    public SuccessResponse<Object> deleteTodo(@PathVariable Long todoId){ //UserDetails 필요
        //4-1. 투투 삭제 서비스 진행
        todoService.deleteTodo(todoId, 1L);

        //4-2. 결과 반환
        return new SuccessResponse<>("투두 삭제", null);
    }



}
