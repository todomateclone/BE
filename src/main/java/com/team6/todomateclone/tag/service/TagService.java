package com.team6.todomateclone.tag.service;

import com.team6.todomateclone.common.exception.CustomErrorException;
import com.team6.todomateclone.member.entity.Member;
import com.team6.todomateclone.member.repository.MemberRepository;
import com.team6.todomateclone.tag.dto.RequestTagDto;
import com.team6.todomateclone.tag.dto.ResponseTagDto;
import com.team6.todomateclone.tag.entity.Tag;
import com.team6.todomateclone.tag.mapper.TagMapper;
import com.team6.todomateclone.tag.repository.TagRepository;
import com.team6.todomateclone.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.team6.todomateclone.common.exception.CustomErrorCodeEnum.MEMBER_NOT_FOUND_MSG;
import static com.team6.todomateclone.common.exception.CustomErrorCodeEnum.TAG_INVALID_PERMISSION_MSG;
import static com.team6.todomateclone.common.exception.CustomErrorCodeEnum.TAG_NOT_DELETE_MSG;
import static com.team6.todomateclone.common.exception.CustomErrorCodeEnum.TAG_NOT_FOUND_MSG;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final MemberRepository memberRepository;
    private final TodoRepository todoRepository;
    private final TagMapper tagMapper;

    // 태그 등록
    @Transactional
    public ResponseTagDto createTag(RequestTagDto requestTagDto, Member member) {
        Tag tag = tagMapper.toEntity(requestTagDto, member);
        tagRepository.save(tag);

        return tagMapper.toResponseTagDto(tag);
    }

    // 태그 조회
    @Transactional(readOnly = true)
    public List<ResponseTagDto> getTag(Long memberId) {
        // 멤버 유효성 검사
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new CustomErrorException(MEMBER_NOT_FOUND_MSG)
        );

        List<ResponseTagDto> tags = new ArrayList<>();
        for (Tag tag : member.getTags()) {
            tags.add(tagMapper.toResponseTagDto(tag));
        }
        return tags;
    }

    // 태그 수정
    @Transactional
    public ResponseTagDto updateTag(Long tagId, RequestTagDto requestTagDto, Member member) {
        // 선택한 태그 존재 여부 확인
        Tag tag = checkTag(tagId);
        // 태그 유효성 검사
        checkPermission(member, tag);

        tag.update(requestTagDto.getTagName(), requestTagDto.getTagColor());

        return tagMapper.toResponseTagDto(tag);
    }

    // 태그 삭제
    @Transactional
    public void deleteTag(Long tagId, Member member) {
        // 선택한 태그 존재 여부 확인
        Tag tag = checkTag(tagId);
        // 태그 유효성 검사
        checkPermission(member, tag);
        // 태그가 하나라도 있으면 삭제 불가
        List<Tag> tagExist = tagRepository.findAll();
        if (tagExist.size()>1) {
            todoRepository.deleteByTagId(tagId);
            tagRepository.deleteById(tagId);
        } else {
            throw new CustomErrorException(TAG_NOT_DELETE_MSG);
        }
    }

    // 태그가 있는지 없는지 확인하는 메서드
    private Tag checkTag(Long tagId) {
        return tagRepository.findById(tagId).orElseThrow(
                () -> new CustomErrorException(TAG_NOT_FOUND_MSG)
        );
    }

    // 태그 유효성 검사 확인하는 메서드
    private void checkPermission(Member member, Tag tag) {
        if(!tag.getMemberId().equals(member.getMemberId())) {
            throw new CustomErrorException(TAG_INVALID_PERMISSION_MSG);
        }
    }
}
