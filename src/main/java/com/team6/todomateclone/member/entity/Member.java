package com.team6.todomateclone.member.entity;

import com.team6.todomateclone.common.TimeStamped;
import com.team6.todomateclone.tag.entity.Tag;
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
public class Member extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(name = "profile_image_url", length = 1000)
    private String profileImageUrl;

    @Column(nullable = false, length = 100)
    private String nickname;

    @Column(length = 100)
    private String description;

    @OneToMany
    @JoinColumn(name = "member_id")
    private List<Todo> todos = new ArrayList<>();


    @OneToMany
    @JoinColumn(name = "member_id")
    private List<Tag> tags = new ArrayList<>();
    
    @Builder
    public Member(String email, String password, String profileImageUrl, String nickname, String description){
        this.email = email;
        this.password = password;
        this.profileImageUrl = profileImageUrl;
        this.nickname = nickname;
        this.description = description;
    }



}
