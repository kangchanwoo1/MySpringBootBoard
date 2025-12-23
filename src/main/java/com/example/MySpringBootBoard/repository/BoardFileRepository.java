package com.example.MySpringBootBoard.repository;

import com.example.MySpringBootBoard.entity.BoardFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardFileRepository extends JpaRepository<BoardFile, Integer> {
    // JpaRepository를 상속받으면 기본적인 CRUD 메서드들을 자동으로 사용할 수 있습니다.
    // 예를 들어, save(), findById(), findAll(), deleteById() 등이 제공됩니다.
}