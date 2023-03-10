package com.team6.todomateclone.tag.mapper;

import com.team6.todomateclone.member.entity.Member;
import com.team6.todomateclone.tag.dto.RequestTagDto;
import com.team6.todomateclone.tag.dto.ResponseTagDto;
import com.team6.todomateclone.tag.entity.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {

    public Tag toEntity(Member member) {
        return Tag.builder()
                .tagName("일반")
                .tagColor("#000000")
                .member(member)
                .build();
    }

    // 태그 등록할 때 Dto -> Entity
    public Tag toEntity(RequestTagDto requestTagDto, Member member) {
        return Tag.builder()
                .tagName(requestTagDto.getTagName())
                .tagColor(requestTagDto.getTagColor())
                .member(member)
                .build();
    }

    // 태그 등록에서 Entity -> Dto
    public ResponseTagDto toResponseTagDto(Tag tag) {
        return ResponseTagDto.builder()
                .tagId(tag.getTagId())
                .tagName(tag.getTagName())
                .tagColor(tag.getTagColor())
                .build();
    }
}
