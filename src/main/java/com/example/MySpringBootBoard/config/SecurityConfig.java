package com.example.MySpringBootBoard.config;

import com.example.MySpringBootBoard.service.UserSecurityService; // UserSecurityService 임포트

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager; // AuthenticationManager 임포트
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration; // AuthenticationConfiguration 임포트
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter; // H2-Console 사용을 위한 임포트
import org.springframework.security.web.util.matcher.AntPathRequestMatcher; // RequestMatcher 임포트 (로그아웃 경로 매칭)

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserSecurityService userSecurityService; // UserSecurityService 주입받기

    // 생성자 주입
    public SecurityConfig(UserSecurityService userSecurityService) {
        this.userSecurityService = userSecurityService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf
                // 💡 H2-Console을 사용하기 위한 CSRF 예외 설정 (주석 해제)
                // .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"))
                .disable() // 일단 CSRF 비활성화 (학습용)
            )
            // 💡 H2-Console 사용을 위한 X-Frame-Options 비활성화 (주석 해제)
            .headers(headers -> headers
                .addHeaderWriter(new XFrameOptionsHeaderWriter(
                    XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)) // SAMEORIGIN으로 설정 (H2-Console 접근 허용)
            )
            .authorizeHttpRequests(authorize -> authorize
                // "/h2-console/**" 경로도 인증 없이 허용 (주석 해제)
                // .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/user/join", "/user/login", "/css/**", "/js/**", "/images/**", "/", "/board/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/user/login") // 로그인 페이지 URL
                .loginProcessingUrl("/user/doLogin") // 💡💡💡 로그인 폼의 POST 요청을 보낼 URL (우리가 임의로 정하는 URL) 💡💡💡
                .defaultSuccessUrl("/board/list", true) // 로그인 성공 시 이동할 URL
                .failureUrl("/user/login?error=true") // 로그인 실패 시 이동할 URL
                .usernameParameter("username") // 💡 로그인 폼에서 사용자 ID 입력 필드의 name 속성 (기본값 username)
                .passwordParameter("password") // 💡 로그인 폼에서 비밀번호 입력 필드의 name 속성 (기본값 password)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")) // 로그아웃 요청 URL
                .logoutSuccessUrl("/user/login?logout=true") // 로그아웃 성공 시 이동할 URL
                .invalidateHttpSession(true) // HTTP 세션 무효화
                .deleteCookies("JSESSIONID") // JSESSIONID 쿠키 삭제
                .permitAll()
            );
        return http.build();
    }

    // ⭐⭐⭐ AuthenticationManager Bean 등록 (로그인 처리 시 사용) ⭐⭐⭐
    // Spring Security의 인증 처리를 담당하는 핵심 매니저입니다.
    // UserSecurityService와 PasswordEncoder를 사용하여 사용자 인증을 처리합니다.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}