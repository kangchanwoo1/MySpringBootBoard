package com.example.MySpringBootBoard.repository; // 네 repository 패키지 이름 확인!

import com.example.MySpringBootBoard.entity.Board; // Board 엔티티 임포트
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository; // JpaRepository 임포트
import org.springframework.stereotype.Repository; // @Repository 어노테이션 임포트

@Repository // 1. 이 인터페이스가 데이터베이스 레포지토리임을 스프링에게 알려줌
public interface BoardRepository extends JpaRepository<Board, Integer> {
	@Override
	Optional<Board> findById(Integer id);
    // JpaRepository<엔티티 클래스, 엔티티의 ID 타입>을 상속받으면 끝!
    // 이렇게만 해도 스프링 데이터 JPA가 자동으로 기본적인 CRUD 메서드를 다 만들어 줘!
    // (예: save(), findById(), findAll(), delete() 등)

    // 만약 "제목으로 게시글 찾기" 같은 커스텀 메서드가 필요하면 여기에 정의해!
    // 예: List<Board> findByTitleContaining(String keyword);
}