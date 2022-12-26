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
import com.team6.todomateclone.todo.repository.TodoRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    /** Repository **/
    private final TagRepository tagRepository;
    private final TodoRespository todoRespository;
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
            todoYear = Long.valueOf(LocalDate.now().getYear());
            todoMonth = Long.valueOf(LocalDate.now().getMonthValue());
        }

        //1-2. 사용자 유효성 검사
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new CustomErrorException(CustomErrorCodeEnum.MEMBER_NOT_FOUND_MSG)
        );

        //1-3. doneCount 계산
        Long doneCount = todoRespository.customTodoDoneCount(memberId, todoYear, todoMonth);
        if (doneCount == null) doneCount = 0L;

        //1-4-1. tags 작업
        List<ResponseGetListTagsDto> tagsDtos = new ArrayList<>();

        //1-4-2. Entity -> tagsDto
        for (Tag tagDto : member.getTags()) {
            tagsDtos.add(getListTodoMapper.toDtoGetListTags(tagDto));
        }

        //1-5-1. todos 작업
        List<ResponseGetListTodosDto> todosDto = new ArrayList<>();
        List<Todo> todos = todoRespository.findAllByMemberIdAndTodoYearAndTodoMonth(member.getMemberId(), todoYear, todoMonth);

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
        todo = todoRespository.save(todo);

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
