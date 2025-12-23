package com.example.MySpringBootBoard.entity;

import jakarta.persistence.*;
import lombok.Getter; // 이 어노테이션은 이제 없어도 되지만, Lombok이 제대로 작동하면 다시 쓰기 위해 남겨둠.
import lombok.Setter; // 이 어노테이션도 마찬가지.
import java.time.LocalDateTime;
import java.util.ArrayList; 
import java.util.List; 

// get, set 사용이유 : private으로 선언 했을때 통제된 접근경로를 제공해서 객체의 상태를 안전하게 읽고 변경할수있게 해줌
@Entity
@Getter // (Lombok 플러그인 해결 전까지는 Eclipse가 인식 못 함)
@Setter // (Lombok 플러그인 해결 전까지는 Eclipse가 인식 못 함)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 게시글 번호

    @Column(length = 200, nullable = false)
    private String title; // 게시글 제목

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content; // 게시글 내용

    @Column(nullable = false)
    private String author; // 작성자

    private LocalDateTime createDate; // 작성일시

    private LocalDateTime modifyDate; // 수정일시 (null 허용)

    // ⭐⭐⭐⭐ 모든 필드에 대한 Getter/Setter 수동 추가 ⭐⭐⭐⭐
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }
    // 💡 게시글에 딸린 파일 목록 (1:N 관계)
    @OneToMany(mappedBy = "board", // BoardFile 엔티티의 "board" 필드에 의해 매핑됨
               cascade = CascadeType.REMOVE, // 게시글 삭제 시 관련 파일들도 함께 삭제
               orphanRemoval = true, fetch = FetchType.EAGER)  // 고아(orphan) 객체(게시글에서 끊어진 파일) 자동 삭제
    private List<BoardFile> boardFileList = new ArrayList<>(); // 초기화!
    
 // Getter 메서드를 추가
    public List<BoardFile> getBoardFileList() {
        return boardFileList;
    }
    
    @PrePersist
    public void prePersist() { // 데이터베이스에 insert되기 직전
        this.createDate = LocalDateTime.now(); // 현재 날짜와 시간을 생성 
    }

    @PreUpdate // 업데이트가 되기전
    public void preUpdate() {
        this.modifyDate = LocalDateTime.now();
    }
    
}