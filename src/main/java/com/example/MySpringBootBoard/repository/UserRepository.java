package com.example.MySpringBootBoard.repository;

import com.example.MySpringBootBoard.entity.User; // User 엔티티 임포트
import org.springframework.data.jpa.repository.JpaRepository; // JpaRepository 임포트
import org.springframework.stereotype.Repository; // @Repository 어노테이션 임포트

import java.util.Optional; // Optional 임포트

// 이 인터페이스가 Spring의 Repository 계층 컴포넌트임을 나타냄
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // JpaRepository<User, Integer>를 상속받으면 User 엔티티 (기본 키는 Integer 타입)에 대한
    // 기본적인 CRUD(생성, 조회, 수정, 삭제) 메서드들이 자동으로 제공

    // ⭐ 로그인 시 사용할 특정 쿼리 메서드 정의 ⭐
    // User 객체를 로그인 ID (username)를 기준으로 찾아 반환하는 메서드
    // Optional을 사용하는 이유는 해당 username을 가진 User가 없을 수도 있기 때문
    Optional<User> findByUsername(String username);
    
    Optional<User> findByEmail(String email); // 이메일로 User를 찾는 메서드
    Optional<User> findByPhoneNumber(String phoneNumber); // 핸드폰 번호로 User를 찾는 메서드
}