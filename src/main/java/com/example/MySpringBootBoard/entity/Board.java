package com.example.MySpringBootBoard.entity;

import jakarta.persistence.*;
import lombok.Getter; // 이 어노테이션은 이제 없어도 되지만, Lombok이 제대로 작동하면 다시 쓰기 위해 남겨둠.
import lombok.Setter; // 이 어노테이션도 마찬가지.
import java.time.LocalDateTime;

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
    // ⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐

    @PrePersist
    public void prePersist() {
        this.createDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.modifyDate = LocalDateTime.now();
    }
}