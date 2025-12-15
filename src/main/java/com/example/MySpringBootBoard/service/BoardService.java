package com.example.MySpringBootBoard.service; // ⭐⭐⭐⭐ 네가 만든 패키지 이름으로 바꿔줘! ⭐⭐⭐⭐

import com.example.MySpringBootBoard.entity.Board; // Board 엔티티 임포트
import com.example.MySpringBootBoard.repository.BoardRepository; // BoardRepository 임포트
//import lombok.RequiredArgsConstructor; // Lombok 어노테이션
import org.springframework.stereotype.Service; // @Service 임포트
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;
import java.util.Optional;

import java.util.List; // List 임포트

//@RequiredArgsConstructor // Lombok: final이 붙은 필드의 생성자를 자동으로 생성
@Service // 이 클래스가 Service 레이어의 빈(Bean)임을 Spring에 알려줌
public class BoardService {

    private final BoardRepository boardRepository;
    
    // ⭐⭐⭐⭐ 수동으로 생성자를 만들어 주입하는 코드 ⭐⭐⭐⭐
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // 모든 게시글 목록을 가져오는 메서드
    public List<Board> getList() {
        // BoardRepository의 findAll() 메서드를 호출하여 모든 Board 엔티티를 List 형태로 반환
        return this.boardRepository.findAll();
    }
    // ⭐⭐⭐⭐ 새롭게 추가: 게시글을 저장하는 메서드 ⭐⭐⭐⭐
    // Board 엔티티를 받아서 DB에 저장하고, 저장된 엔티티를 반환
    public Board saveBoard(String title, String content, String author) { //Controller에서 넘어온 제목, 내용, 작성자 데이터를 받아서 새로운 Board 객체를 생성
        Board board = new Board();
        board.setTitle(title); //전달받은 데이터로 Board 객체의 필드를 채워 넣음. (Board 엔티티에 수동으로 만든 Setter 메서드들이 여기서 사용)
        board.setContent(content);
        board.setAuthor(author);
        // createDate와 modifyDate는 Board 엔티티의 @PrePersist/@PreUpdate로 자동 설정됨

        // BoardRepository의 save() 메서드를 사용하여 DB에 저장
        return this.boardRepository.save(board); //BoardRepository의 save() 메서드를 호출해서 board 객체를 데이터베이스에 저장함. 이 메서드는 Spring Data JPA가 자동으로 제공
    }
    // ⭐⭐⭐⭐ 새롭게 추가: 특정 ID의 게시글을 가져오는 메서드 ⭐⭐⭐⭐
    public Board getBoard(Integer id) {
        // 💡 디버깅 코드 시작!
        System.out.println("DEBUG: BoardService.getBoard 메서드 진입 - 요청 ID: " + id);
        Optional<Board> optionalBoard = boardRepository.findById(id);
        // findById는 Optional<Board>를 반환. 값이 없을 경우 예외 처리
        Optional<Board> boardOptional = this.boardRepository.findById(id);
        if (boardOptional.isPresent()) {
            return boardOptional.get();
        } else {
        	return null;
        }
    }
    // 💡 4. 게시글 수정 메서드 (누락되었을 수 있는 이 부분!)
    @Transactional // ⚠️ 트랜잭션 처리를 위해 추가!
    public void updateBoard(Board board) {
        boardRepository.findById(board.getId()).ifPresent(existingBoard -> {
            existingBoard.setTitle(board.getTitle());
            existingBoard.setAuthor(board.getAuthor());
            existingBoard.setContent(board.getContent());
            existingBoard.setModifyDate(LocalDateTime.now()); // 수정 시간 기록
            boardRepository.save(existingBoard);
        });
    }
     // 💡 5. 게시글 삭제 메서드
        public void deleteBoard(Integer id) {
            System.out.println("DEBUG: BoardService.deleteBoard 메서드 진입 - 삭제 요청 ID: " + id); // 디버그 추가
            boardRepository.deleteById(id); // Spring Data JPA에서 제공하는 ID로 삭제 메서드
            System.out.println("DEBUG: BoardService.deleteBoard - 게시글 ID " + id + " 삭제 완료."); // 디버그 추가
        
}
    
}