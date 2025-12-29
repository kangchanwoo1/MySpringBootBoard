package com.example.MySpringBootBoard.service;

import com.example.MySpringBootBoard.entity.User;
import com.example.MySpringBootBoard.repository.UserRepository;
import jakarta.transaction.Transactional; // 트랜잭션 처리를 위한 임포트 (데이터 변경 시 필수)
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder; // 비밀번호 암호화를 위한 임포트

@Service
public class UserService {
    private final UserRepository userRepository; // UserRepository를 주입받아 데이터베이스와 User 엔티티의 상호작용을 담당
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화를 담당하는 PasswordEncoder를 주입
    
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
        // 회원가입 로직을 처리하는 메서드
        @Transactional // 이 메서드 내의 모든 데이터 변경 작업은 하나의 트랜잭션으로 묶음
        public User join(User user) {
            // 💡 아이디(username) 중복 검사
            userRepository.findByUsername(user.getUsername()).ifPresent(u -> {
                throw new IllegalStateException("이미 존재하는 아이디입니다.");
            });
            
            // 💡 이메일 중복 검사 (추가)
            userRepository.findByEmail(user.getEmail()).ifPresent(u -> {
                throw new IllegalStateException("이미 존재하는 이메일입니다.");
            });

            // 💡 핸드폰 번호 중복 검사 (추가)
            userRepository.findByPhoneNumber(user.getPhoneNumber()).ifPresent(u -> {
                throw new IllegalStateException("이미 존재하는 핸드폰 번호입니다.");
            });

            // 비밀번호를 암호화하여 저장합니다. (평문 비밀번호 노출 방지)
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            
            // 데이터베이스에 User 엔티티를 저장하고, 저장된 User 객체를 반환합니다.
            return userRepository.save(user);
        }

        // ⭐ 추후 로그인 시 사용할 User 객체를 ID(username)로 조회하는 메서드 ⭐
        public User findByUsername(String username) {
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new IllegalStateException("존재하지 않는 사용자입니다."));
        }
    }
