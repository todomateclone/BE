package com.team6.todomateclone.todo.entity;

import com.team6.todomateclone.common.TimeStamped;
import lombok.AccessLevel;
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
}
