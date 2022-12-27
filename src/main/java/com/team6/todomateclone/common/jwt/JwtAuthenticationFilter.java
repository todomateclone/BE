package com.team6.todomateclone.common.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team6.todomateclone.common.exception.CustomErrorCodeEnum;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.team6.todomateclone.common.exception.CustomErrorCodeEnum.TOKEN_NOT_FOUND_MSG;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = jwtUtil.resolveToken(request);

        if (token == null) {
            filterChain.doFilter(request, response);
        }

        if (!jwtUtil.validateAccessToken(token)) {
            jwtExceptionHandler(response, TOKEN_NOT_FOUND_MSG);
            return;
        }

        // 사용자 인증
        Claims claims = jwtUtil.getUserInfoFromToken(token);
        setAuthentication(response, claims.getSubject());
        filterChain.doFilter(request, response);
    }

    // 인증, 인가 설정
    private void setAuthentication(HttpServletResponse response, String email) {
        // security context 생성
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        // Authentication 생성
        Authentication authentication = jwtUtil.createAuthentication(email);
        // context 에 인증 객체 삽입
        context.setAuthentication(authentication);
        // security context holder 에 context 삽입
        SecurityContextHolder.setContext(context);
    }

    public void jwtExceptionHandler(HttpServletResponse response, CustomErrorCodeEnum errorCodeEnum) {
        response.setStatus(400);
        response.setContentType("application/json; charset=utf8");
        try {
            String json = new ObjectMapper().writeValueAsString(errorCodeEnum.getMsg());
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
