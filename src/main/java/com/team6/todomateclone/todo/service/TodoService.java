package com.team6.todomateclone.todo.service;

import com.team6.todomateclone.common.exception.CustomErrorCodeEnum;
import com.team6.todomateclone.common.exception.CustomErrorException;
import com.team6.todomateclone.member.entity.Member;
import com.team6.todomateclone.member.repository.MemberRepository;
import com.team6.todomateclone.tag.entity.Tag;
import com.team6.todomateclone.tag.repository.TagRepository;
import com.team6.todomateclone.todo.dto.GetListDto.ResponseGetListTagsDto;

import com.team6.todomateclone.todo.dto.GetListDto.ResponseGetListTodoDto;
import com.team6.todomateclone.todo.dto.GetListDto.ResponseGetListTodosDto;
import com.team6.todomateclone.todo.dto.createdto.CreateTodoDto;
import com.team6.todomateclone.todo.dto.createdto.ResponseCreateTodoDto;
import com.team6.todomateclone.todo.dto.updatedto.ResponseUpdateTodoDto;
import com.team6.todomateclone.todo.dto.updatedto.UpdateTodoDto;
import com.team6.todomateclone.todo.entity.Todo;
import com.team6.todomateclone.todo.mapper.CreateTodoMapper;
import com.team6.todomateclone.todo.mapper.GetListTodoMapper;
import com.team6.todomateclone.todo.mapper.UpdateTodoMapper;
import com.team6.todomateclone.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.team6.todomateclone.common.exception.CustomErrorCodeEnum.MEMBER_NOT_FOUND_MSG;
import static com.team6.todomateclone.common.exception.CustomErrorCodeEnum.TODO_INVALID_PERMISSION_MSG;
import static com.team6.todomateclone.common.exception.CustomErrorCodeEnum.TODO_NOT_FOUND_MSG;

@Service
@RequiredArgsConstructor
public class TodoService {

    /** Repository **/
    private final TagRepository tagRepository;
    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;

    /** Mapper **/
    private final CreateTodoMapper createTodoMapper;
    private final UpdateTodoMapper updateTodoMapper;
    private final GetListTodoMapper getListTodoMapper;

    /** 1. 전체 조회(월단위) **/
    @Transactional(readOnly = true)
    public ResponseGetListTodoDto getTodos(Long todoYear, Long todoMonth, Long memberId) {
        //1-1. 검색 조건 Date 준비
        //날짜 파라미터가 null이라면 오늘 날짜 기준 year, month 저장
        if (todoYear == null || todoMonth == null) {
            todoYear = (long) LocalDate.now().getYear();
            todoMonth = (long) LocalDate.now().getMonthValue();
        }

        //1-2. 사용자 유효성 검사
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new CustomErrorException(MEMBER_NOT_FOUND_MSG)
        );

        //1-3. doneCount 계산
        Long doneCount = todoRepository.customTodoDoneCount(memberId, todoYear, todoMonth);
        if (doneCount == null) doneCount = 0L;

        //1-4-1. tags 작업
        List<ResponseGetListTagsDto> tagsDtos = new ArrayList<>();

        //1-4-2. Entity -> tagsDto
        for (Tag tagDto : member.getTags()) {
            tagsDtos.add(getListTodoMapper.toDtoGetListTags(tagDto));
        }

        //1-5-1. todos 작업
        List<ResponseGetListTodosDto> todosDto = new ArrayList<>();
        List<Todo> todos = todoRepository.findAllByMemberIdAndTodoYearAndTodoMonth(member.getMemberId(), todoYear, todoMonth);

        //1-5-2. Entity -> todosDto
        for (Todo todo : todos) {
            todosDto.add(getListTodoMapper.toDtopGetListTodos(todo));
        }


        //1-6. Entity -> ResponseDto 및 최종 ResponseDto 작업
        ResponseGetListTodoDto responseGetListTodoDto = getListTodoMapper.toDtopGetListTodo(member, doneCount, tagsDtos, todosDto);

        //1-7. 결과 반환
        return responseGetListTodoDto;
    }

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
        todo = todoRepository.save(todo);

        //2-4. Entity -> ServiceDtojsd
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
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new CustomErrorException(TODO_NOT_FOUND_MSG)
        );

        //3-2. 투두 수정 권한 여부 검사
        if (!memberId.equals(todo.getMemberId())){
            throw new CustomErrorException(TODO_INVALID_PERMISSION_MSG);
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
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new CustomErrorException(TODO_NOT_FOUND_MSG)
        );

        //4-2. 투두 삭제 권한 여부 검사
        if (!memberId.equals(todo.getMemberId())){
            throw new CustomErrorException(TODO_INVALID_PERMISSION_MSG);
        }

        //4-3. 투두 삭제
        todoRepository.deleteById(todo.getTodoId());
    }

    /** 5. 투두 달성 **/
    @Transactional
    public String updateTodoDone(Long todoId, Long memberId) {
        //5-1. 투두 유효성 검사 및 done 조회
        Todo todo = todoRepository.findByTodoIdAndMemberId(todoId, memberId).orElseThrow(
                () -> new CustomErrorException(TODO_NOT_FOUND_MSG)
        );

        //5-2. 투두 done 수정(Toggle 방식)
        String msg ="";

        if(todo.isDone() == false) {
            todo.updateTodoDone(true);
            msg = "Todo done 변경 성공하였습니다.";
        } else if(todo.isDone() == true){
            todo.updateTodoDone(false);
            msg = "Todo done 변경 취소하였습니다.";
        }

        //5-3. 투두 done 결과 반환
        return msg;
    }
}
