package com.team6.todomateclone.todo.controller;

import com.team6.todomateclone.common.response.SuccessResponse;
import com.team6.todomateclone.todo.dto.createdto.CreateTodoDto;
import com.team6.todomateclone.todo.dto.createdto.RequestCreateTodoDto;
import com.team6.todomateclone.todo.dto.RequestTodoUpdateDto;
import com.team6.todomateclone.todo.dto.createdto.ResponseCreateTodoDto;
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
        return new SuccessResponse<>("투두 등록", data);
    }

    //3. 투두 수정
    @PutMapping("/{todoId}")
    public SuccessResponse<Object> updateTodo(@PathVariable Long todoId, @RequestBody @Valid RequestTodoUpdateDto todoUpdateDto){//UserDetails 필요
        System.out.println("updateTodo : " + todoId);
        return new SuccessResponse<>("투두 수정", null);
    }

    //4. 투두 삭제
    @DeleteMapping("/{todoId}")
    public SuccessResponse<Object> deleteTodo(@PathVariable Long todoId){ //UserDetails 필요
        System.out.println("deleteTodo : " + todoId);
        return new SuccessResponse<>("투두 삭제", null);
    }



}
