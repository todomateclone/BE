package com.team6.todomateclone.todo.entity;

import com.team6.todomateclone.common.TimeStamped;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private Long todoId;

    @Column(nullable = false)
    private String content;

    @Column(name = "todo_year", nullable = false)
    private Long todoYear;

    @Column(name = "todo_month", nullable = false)
    private Long todoMonth;

    @Column(name = "todo_day", nullable = false)
    private Long todoDay;

    @Column(nullable = false)
    private boolean done;

    @Column(name = "tag_id", nullable = false)
    private Long tagId;
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Builder
    public Todo(Long todoId, String content, Long todoYear, Long todoMonth, Long todoDay, boolean done, Long tagId, Long memberId) {
        this.todoId = todoId;
        this.content = content;
        this.todoYear = todoYear;
        this.todoMonth = todoMonth;
        this.todoDay = todoDay;
        this.done = done;
        this.tagId = tagId;
        this.memberId = memberId;
    }


    public void updateTodo(Todo updateTodo) {
        this.content = updateTodo.getContent();
        this.todoYear = updateTodo.getTodoYear();
        this.todoMonth = updateTodo.getTodoMonth();
        this.todoDay = updateTodo.getTodoDay();
        this.done = updateTodo.isDone();
    }

    public void updateTodoDone(boolean todoDone) {
        this.done = todoDone;
    }
}
