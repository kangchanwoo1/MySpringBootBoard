package com.example.MySpringBootBoard.entity; // 네 entity 패키지 이름 확인!

import java.time.LocalDateTime; // 자바 8 날짜/시간 API

import org.hibernate.annotations.CreationTimestamp; // 날짜 자동 생성 어노테이션

// JPA 관련 어노테이션 임포트
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
// Lombok 관련 어노테이션 임포트
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity // 1. 이 클래스가 JPA 엔티티임을 나타냄 (DB 테이블과 매핑됨)
@Getter // 2. 모든 필드의 Getter 메서드를 자동 생성
@Setter // 3. 모든 필드의 Setter 메서드를 자동 생성
@ToString // 4. toString() 메서드를 자동 생성 (로그 출력 시 편리)
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 5. 파라미터 없는 기본 생성자 자동 생성 (JPA 필수)
@AllArgsConstructor // 6. 모든 필드를 파라미터로 받는 생성자 자동 생성
@Builder // 7. 빌더 패턴으로 객체 생성 가능하게 함 (유연하게 객체 생성)
public class Board {

    @Id // 8. 기본 키(Primary Key) 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 9. 기본 키 자동 생성 전략 (MySQL의 AUTO_INCREMENT)
    private Long id; // 게시글 번호 (Primary Key)

    @Column(nullable = false, length = 100) // 10. DB 컬럼 속성 (null 불가, 길이 100)
    private String title; // 게시글 제목

    @Column(nullable = false, length = 1000) // 11. DB 컬럼 속성 (null 불가, 길이 1000)
    private String content; // 게시글 내용

    @Column(length = 50) // 12. DB 컬럼 속성 (길이 50)
    private String writer; // 작성자

    @CreationTimestamp // 13. 엔티티가 생성될 때 현재 시간을 자동으로 설정
    @Column(updatable = false) // 14. 한번 저장되면 업데이트되지 않도록 설정
    private LocalDateTime createdDate; // 생성일시

    // (필요하다면 여기에 조회수, 수정일 등 추가 가능)

}