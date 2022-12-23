package com.team6.todomateclone.member.repository;

import com.team6.todomateclone.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
