package com.team6.todomateclone.common.jwt.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team6.todomateclone.common.exception.CustomErrorException;
import com.team6.todomateclone.common.exception.CustomErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Slf4j
@RequiredArgsConstructor
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (CustomErrorException e) {
            log.error("JWT 인증 / 인가 관련 이슈");
            setErrorResponse(response, e);
        }
    }

    private void setErrorResponse(HttpServletResponse response, CustomErrorException e) throws IOException {
        // JSON Type 반환 명시
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // HttpStatus 설정 -> 에러메시지 수정 필요
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        CustomErrorResponse errorResponse = CustomErrorResponse.builder()
                .result("fail")
                .msg(e.getMessage())
                .code(401)
                .build();

        try (OutputStream os = response.getOutputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(os, errorResponse);
            os.flush();
        }
    }
}
