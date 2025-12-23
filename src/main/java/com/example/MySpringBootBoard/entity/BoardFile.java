package com.example.MySpringBootBoard.entity;

import jakarta.persistence.*;
// import lombok.Getter; // 💡 주석 처리하거나 지우세요!
// import lombok.Setter; // 💡 주석 처리하거나 지우세요!

@Entity
// @Getter // 💡 주석 처리하거나 지우세요!
// @Setter // 💡 주석 처리하거나 지우세요!
public class BoardFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String originalFileName;
    private String storedFileName;
    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    // 💡 아래부터 Getter/Setter 메서드들을 직접 추가하세요!

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getStoredFileName() {
        return storedFileName;
    }

    public void setStoredFileName(String storedFileName) {
        this.storedFileName = storedFileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}