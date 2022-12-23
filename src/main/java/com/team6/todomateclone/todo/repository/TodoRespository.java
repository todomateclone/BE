package com.team6.todomateclone.todo.repository;

import com.team6.todomateclone.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRespository extends JpaRepository<Todo,Long> {
}
