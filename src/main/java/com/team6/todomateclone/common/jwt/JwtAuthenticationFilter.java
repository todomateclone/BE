package com.team6.todomateclone.common.jwt;

import com.team6.todomateclone.common.exception.CustomErrorCodeEnum;
import com.team6.todomateclone.common.exception.CustomErrorException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.team6.todomateclone.common.exception.CustomErrorCodeEnum.INVALID_TOKEN;
import static com.team6.todomateclone.common.exception.CustomErrorCodeEnum.TOKEN_NOT_FOUND;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final Set<String> skipFilterUrls = new HashSet<>(Arrays.asList("/api/auth/**", "/"));
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, CustomErrorException {

        String method = request.getMethod();

        // pre-flight 요청일 때, JwtAuthenticationFilter 건너뜀.
        if (method.equals("OPTIONS")) {
            return;
        }

        // Request 토큰 추출
        String token = jwtUtil.resolveToken(request, "Authorization");

        // Token 유효성 검사 및 인증
        if (token == null) {
            throw new CustomErrorException(TOKEN_NOT_FOUND);
        }

        // Token 유효성 확인
        if (!jwtUtil.validateAccessToken(token, request, response)) {
            throw new CustomErrorException(INVALID_TOKEN);
        }

        // 사용자 인증
        Claims claims = jwtUtil.getUserInfoFromHttpServletRequest(request);
        setAuthentication(claims.getSubject());
    }

    // 인증, 인가 설정
    private void setAuthentication(String email) {
        // security context 생성
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        // Authentication 생성
        Authentication authentication = jwtUtil.createAuthentication(email);
        // context 에 인증 객체 삽입
        context.setAuthentication(authentication);
        // security context holder 에 context 삽입
        SecurityContextHolder.setContext(context);
    }

    // JwtAuthenticationFilter 필터링 예외 처리
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return skipFilterUrls.stream()
                .anyMatch(p -> pathMatcher.match(p, request.getRequestURI()));
    }
}
