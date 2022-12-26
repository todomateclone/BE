package com.team6.todomateclone.common.security;

import com.team6.todomateclone.member.entity.Member;
import com.team6.todomateclone.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.team6.todomateclone.common.exception.CustomErrorCodeEnum.MEMBER_NOT_FOUND_MSG;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // 사용자 조회
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(MEMBER_NOT_FOUND_MSG.getMsg()));

        // UserDetailsImpl 반환
        return new UserDetailsImpl(member, member.getEmail(), member.getPassword());
    }
}
