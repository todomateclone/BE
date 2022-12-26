package com.team6.todomateclone.tag.entity;

import com.team6.todomateclone.todo.entity.Todo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long tagId;

    @Column(nullable = false, length = 100)
    private String tagName;

    @Column(nullable = false, length = 100)
    private String tagColor;

    @OneToMany
    @JoinColumn(name = "tag_id")
    private List<Todo> todos = new ArrayList<>();

    @Builder
    public Tag(String tagName, String tagColor) {
        this.tagName = tagName;
        this.tagColor = tagColor;
    }

    public void update(String tagName, String tagColor) {
        this.tagName = tagName;
        this.tagColor = tagColor;
    }
}
