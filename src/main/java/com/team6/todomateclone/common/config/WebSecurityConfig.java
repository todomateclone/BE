package com.team6.todomateclone.common.config;

import com.team6.todomateclone.common.jwt.JwtAuthenticationFilter;
import com.team6.todomateclone.common.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {
    private final JwtUtil jwtUtil;

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 패스워드 인코딩 -> bcrypt 형식 사용
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring()
//                // h2 설정
//                .requestMatchers(PathRequest.toH2Console())
//                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF(Cross-site request forgery) 비활성화 설정
        http.csrf().disable()
                // cors 설정
                .cors().configurationSource(corsConfigurationSource())
                .and()
                // 세션 비활성화
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 로그인 인증, 인가
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/auth/**").permitAll() // 로그인, 회원가입 uri 모두 인증 제외
                .antMatchers(HttpMethod.GET, "/").permitAll() // HttpsHealthyCheck 요청 허용
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll() // pre-flight 요청 허용
                .anyRequest().authenticated() // 위에 적힌 permitAll 을 제외한 어떤 요청이든 인증 진행
                .and()
                // JWT Filter 등록
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // cors 설정
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedOrigin("http://localhost:3001");
        configuration.addAllowedOrigin("http://localhost:3002");
        configuration.addAllowedOrigin("http://localhost:3003");
        configuration.addAllowedOrigin("http://localhost:3004");
        configuration.addAllowedOrigin("http://localhost:3005");
        configuration.addAllowedMethod("*"); // 허용할 Http Method
        configuration.addAllowedHeader("*"); // 허용할 헤더
        configuration.setAllowCredentials(true); // 내 서버가 응답할 때 json 을 js 에서 처리할 수 있게 설정
        configuration.setMaxAge(3600L); // 쿠키 유효 기간 (60 * 60 = 1시간)
        configuration.addExposedHeader("Authorization"); // 응답에 노출되는 헤더
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration); // 위 적용값들을 해당 uri 에 전부 적용.
        return source;
    }
}
