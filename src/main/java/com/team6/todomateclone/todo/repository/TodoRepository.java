package com.team6.todomateclone.todo.repository;

import com.team6.todomateclone.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Query(value = "SELECT count(done) doneCount FROM todo" +
            " GROUP BY member_id, done, todo_year, todo_month" +
            " HAVING member_id = :memberId AND done = true AND todo_year = :todoYear AND todo_month = :todoMonth",
            nativeQuery = true)
    Long  customTodoDoneCount(@Param("memberId") Long memberId,
                             @Param("todoYear") Long todoYear,
                             @Param("todoMonth") Long todoMonth);

    List<Todo> findAllByMemberIdAndTodoYearAndTodoMonth(Long memberId, Long todoYear, Long todoMonth);

    void deleteAllByTagId(Long tagId);
}
