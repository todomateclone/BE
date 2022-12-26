package com.team6.todomateclone.tag.service;

import com.team6.todomateclone.common.exception.CustomErrorCodeEnum;
import com.team6.todomateclone.common.exception.CustomErrorException;
import com.team6.todomateclone.tag.dto.RequestTagDto;
import com.team6.todomateclone.tag.dto.ResponseTagDto;
import com.team6.todomateclone.tag.entity.Tag;
import com.team6.todomateclone.tag.mapper.TagMapper;
import com.team6.todomateclone.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    // 태그 등록
    @Transactional
    public List<ResponseTagDto> createTag(RequestTagDto requestTagDto) {
        Tag tag = tagMapper.toEntity(requestTagDto);
        tagRepository.save(tag);
        List<Tag> tagList = tagRepository.findAll();
        List<ResponseTagDto> result = new ArrayList<>();
        for (Tag tagValue : tagList) {
            result.add(tagMapper.toResponseTagDtoCreate(tagValue));
        }
        return result;
    }

    // 태그 수정
    @Transactional
    public ResponseTagDto updateTag(Long tagId, RequestTagDto requestTagDto) {
        Tag tag = checkTag(tagId);

        tag.update(requestTagDto.getTagName(), requestTagDto.getTagColor());

        return tagMapper.toResponseTagDtoCreate(tag);
    }

    // 태그 삭제
    @Transactional
    public void deleteTag(Long tagId) {
        checkTag(tagId);

        tagRepository.deleteById(tagId);
    }

    // 태그가 있는지 없는지 확인하는 메서드
    private Tag checkTag(Long tagId) {
        Tag tag = tagRepository.findById(tagId).orElseThrow(
                () -> new CustomErrorException(CustomErrorCodeEnum.TAG_NOT_FOUND_MSG)
        );
        return tag;
    }

}
