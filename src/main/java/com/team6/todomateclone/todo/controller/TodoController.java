package com.team6.todomateclone.todo.controller;

import com.team6.todomateclone.common.exception.ValidationSequence;
import com.team6.todomateclone.common.response.SuccessResponse;
import com.team6.todomateclone.common.security.UserDetailsImpl;
import com.team6.todomateclone.todo.dto.GetListDto.ResponseGetListTodoDto;
import com.team6.todomateclone.todo.dto.createdto.CreateTodoDto;
import com.team6.todomateclone.todo.dto.createdto.RequestCreateTodoDto;
import com.team6.todomateclone.todo.dto.createdto.ResponseCreateTodoDto;
import com.team6.todomateclone.todo.dto.updatedto.RequestUpdateTodoDto;
import com.team6.todomateclone.todo.dto.updatedto.ResponseUpdateTodoDto;
import com.team6.todomateclone.todo.dto.updatedto.UpdateTodoDto;
import com.team6.todomateclone.todo.service.TodoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags={"메인페이지 Todo API Controller"})
@RestController
@RequestMapping("/api/todo")
@RequiredArgsConstructor
@Validated //@PathVariable validation 적용은 class에 @Validated를 적용해야함
public class TodoController {

    private final TodoService todoService;

    /** 1. 투투 전체조회(월단위) **/
    @ApiOperation(value = "투두 전체 조회(월단위)")
    @GetMapping(value = {"", "/{todoYear}/{todoMonth}"}) //다중 매핑
    public SuccessResponse<ResponseGetListTodoDto> getTodos(@PathVariable(required = false) //@PathVariable 변수에 null값 저장 위해 사용
                                                            @Range(min = 1980, max = 3000, message = "년도는 1980 ~ 3000년까지 입력 가능합니다.")
                                                            Long todoYear,
                                                            @PathVariable(required = false)
                                                            @Range(min = 1, max = 12, message = "월은 1 ~ 12월까지 입력 가능합니다.")
                                                            Long todoMonth,
                                                            @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        //1-1. 전체 조회 서비스 진행
        ResponseGetListTodoDto data = todoService.getTodos(todoYear, todoMonth, userDetails.getMember().getMemberId());

        //1-2. 결과 반환
        return new SuccessResponse<>("메인페이지 조회 성공하였습니다.", data);
    }

    /** 2. 투두 등록 **/
    @ApiOperation(value = "투두 등록")
    @PostMapping("/{tagId}")
    public SuccessResponse<ResponseCreateTodoDto> createTodo(@PathVariable Long tagId,
                                                             @RequestBody @Validated(ValidationSequence.class) RequestCreateTodoDto requestCreateTodoDto,
                                                             @AuthenticationPrincipal UserDetailsImpl userDetails){
        //2-1. RequestDto -> ServiceDto
        CreateTodoDto createTodoDto = requestCreateTodoDto.toCreateTodoDto();

        //2-2. 투두 등록 서비스 진행
        ResponseCreateTodoDto data = todoService.createTodo(tagId, userDetails.getMember().getMemberId(), createTodoDto);

        //2-3. 결과 반환
        return new SuccessResponse<>("일정 등록 성공하였습니다.", data);
    }

    /** 3. 투두 수정 **/
    @ApiOperation(value = "투두 수정")
    @PutMapping("/{todoId}")
    public SuccessResponse<ResponseUpdateTodoDto> updateTodo(@PathVariable Long todoId,
                                                             @RequestBody @Validated(ValidationSequence.class) RequestUpdateTodoDto requestUpdateTodoDto,
                                                             @AuthenticationPrincipal UserDetailsImpl userDetails){
        //3-1. RequestDto -> ServiceDto
        UpdateTodoDto updateTodoDto = requestUpdateTodoDto.toUpdateTodoDto();

        //3-2. 투두 수정 서비스 진행
        ResponseUpdateTodoDto data = todoService.updateTodo(todoId, userDetails.getMember().getMemberId(), updateTodoDto);

        //3-3. 결과 반환
        return new SuccessResponse<>("일정 수정 성공하였습니다.", data);
    }

    /** 4. 투두 삭제 **/
    @ApiOperation(value = "투두 삭제")
    @DeleteMapping("/{todoId}")
    public SuccessResponse<Object> deleteTodo(@PathVariable Long todoId,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails){
        //4-1. 투투 삭제 서비스 진행
        todoService.deleteTodo(todoId, userDetails.getMember().getMemberId());

        //4-2. 결과 반환
        return new SuccessResponse<>("일정 삭제 성공하였습니다.", null);
    }
}
