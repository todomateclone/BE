package com.team6.todomateclone.tag.repository;

import com.team6.todomateclone.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
