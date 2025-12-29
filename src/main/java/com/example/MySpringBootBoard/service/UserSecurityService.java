package com.example.MySpringBootBoard.service;

import com.example.MySpringBootBoard.entity.User;
import com.example.MySpringBootBoard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService; // 핵심 인터페이스 임포트
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections; // Collection 유틸리티 임포트

@Service // 이 클래스가 Spring의 서비스 계층 컴포넌트임을 나타냅니다.
public class UserSecurityService implements UserDetailsService { // UserDetailsService 인터페이스 구현

    private final UserRepository userRepository;

    @Autowired
    public UserSecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ⭐⭐⭐ loadUserByUsername 메서드를 구현하여 사용자 정보를 로드 ⭐⭐⭐
    // 사용자가 입력한 username(아이디)을 기반으로 DB에서 사용자 정보를 조회하여 Spring Security가 사용할 수 있는 UserDetails 객체로 반환합니다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // UserRepository를 사용하여 DB에서 사용자 정보를 조회합니다.
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username)); // 사용자가 없으면 예외 발생

        // Spring Security의 User 객체 (UserDetails 구현체)를 생성하여 반환합니다.
        // 여기서는 권한(roles)을 "ROLE_USER"로 임시 설정합니다. (나중에 역할 관리가 필요하면 확장)
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),      // 사용자명 (로그인 ID)
                user.getPassword(),      // 암호화된 비밀번호
                Collections.emptyList()  // 사용자 권한 목록 (현재는 비어 있음)
        );
    }
}