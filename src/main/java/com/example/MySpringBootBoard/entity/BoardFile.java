package com.example.MySpringBootBoard.entity;

import jakarta.persistence.*;


@Entity

public class BoardFile { // 첨부파일의 정보를 데이터베이스의 저장하기 위한 엔티티, 첨부파일하나하나의 고유한 정보덩어리
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id필드가 테이블의 기본키 이며, 값이 자동증가방식으로 생성되도록 설정
    private Integer id;

    private String originalFileName; // 사용자가 파일을 업로드할때 원래 파일이름
    private String storedFileName; // 서버에 실제로 저장될때 파일이름
    private String filePath; // 파일이 서버의 어떤 경로에 저장되었는지 알려주는 필드

    @ManyToOne // 여러파일(BoardFile)이 하나의 게시글(Board)에 속할 수 있다는 의미
    (fetch = FetchType.LAZY) // BoardFile 엔티티를 조회할 때, 당장 Board 객체가 필요하지 않으면 같이 로딩하지 않고, 실제로 board 필드에 접근할 때 로딩
    @JoinColumn(name = "board_id") //BoardFile 테이블에 board_id라는 외래 키 컬럼을 만들어서 Board 테이블의 기본 키와 연결
    private Board board;

    // 💡 아래부터 Getter/Setter 메서드들을 직접 추가

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