package com.example.MySpringBootBoard.service; 

import com.example.MySpringBootBoard.entity.Board;
import com.example.MySpringBootBoard.entity.BoardFile;
import com.example.MySpringBootBoard.repository.BoardFileRepository;
import com.example.MySpringBootBoard.repository.BoardRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List; // List, ArrayList 사용을 위해 필요
import java.util.Optional;
import java.util.UUID; // 고유 파일 이름 생성을 위해 필요
import java.util.ArrayList; // boardFileList 초기화를 위해 필요
import org.hibernate.Hibernate;


//@RequiredArgsConstructor // Lombok: final이 붙은 필드의 생성자를 자동으로 생성
@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardFileRepository boardFileRepository;

    // application.properties에서 설정한 파일 저장 경로 가져오기
    @Value("${file.dir}")
    private String fileDir;

    // 1. 게시글 저장 메서드 (파일 업로드 기능 포함)
    @Transactional
    public void write(Board board, List<MultipartFile> files) throws IOException {
        boardRepository.save(board); // 게시글 먼저 저장 -> board ID 생성됨 (이게 핵심!)

        if (files != null && !files.isEmpty()) {
            // 💡 파일 저장 디렉토리 생성 로직 추가
            File uploadPath = new File(fileDir);
            if (!uploadPath.exists()) { // uploads 폴더가 존재하지 않으면
                uploadPath.mkdirs(); // uploads 폴더를 생성 (하위 폴더도 포함)
            }
            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    continue; // 파일이 비어있으면 건너뛰기
                }

                String originalFileName = file.getOriginalFilename(); // 원본 파일 이름
                // 서버에 저장할 고유한 파일 이름 생성 (UUID 사용)
                String storedFileName = UUID.randomUUID().toString() + "_" + originalFileName;
                String filePath = fileDir + storedFileName; // 파일 저장 경로

                // 파일 서버 폴더에 실제 저장
                file.transferTo(new File(filePath));

                // 파일 정보 DB에 저장할 BoardFile 엔티티 생성 및 관계 설정
                BoardFile boardFile = new BoardFile();
                boardFile.setOriginalFileName(originalFileName);
                boardFile.setStoredFileName(storedFileName);
                boardFile.setFilePath(filePath);
                boardFile.setBoard(board); // 💡 이 파일이 어느 게시글에 속하는지 Board 엔티티 설정

                boardFileRepository.save(boardFile); // BoardFile 엔티티 저장
            }
        }
    }


    // 2. 모든 게시글 불러오기 메서드 (기존 boardList() 또는 getList()와 동일 역할)
    public List<Board> boardList() {
        return boardRepository.findAll();
    }


    // 3. 특정 ID의 게시글 불러오기 메서드
    public Board getBoard(Integer id) {
        System.out.println("DEBUG: BoardService.getBoard 메서드 진입 - 요청 ID: " + id);
        Optional<Board> optionalBoard = boardRepository.findById(id);

        if (optionalBoard.isPresent()) {
            Board foundBoard = optionalBoard.get();
            System.out.println("DEBUG: BoardService.getBoard - ID " + id + " 게시글 발견. 제목: " + foundBoard.getTitle());
            Hibernate.initialize(foundBoard.getBoardFileList());
            return foundBoard;
        } else {
            System.out.println("DEBUG: BoardService.getBoard - ID " + id + " 게시글을 찾을 수 없습니다.");
            return null;
        }
    }

    // 4. 게시글 수정 메서드
    @Transactional
    public void updateBoard(Board board) {
        boardRepository.findById(board.getId()).ifPresent(existingBoard -> {
            existingBoard.setTitle(board.getTitle());
            existingBoard.setAuthor(board.getAuthor());
            existingBoard.setContent(board.getContent());
            existingBoard.setModifyDate(LocalDateTime.now()); // 수정 시간 기록
            boardRepository.save(existingBoard);
        });
    }

    // 5. 게시글 삭제 메서드
    public void deleteBoard(Integer id) {
        System.out.println("DEBUG: BoardService.deleteBoard 메서드 진입 - 삭제 요청 ID: " + id);
        boardRepository.deleteById(id);
        System.out.println("DEBUG: BoardService.deleteBoard - 게시글 ID " + id + " 삭제 완료.");
    }

    // 💡 주석처리된 예전 saveBoard 메서드는 제거했습니다. write(Board, List<MultipartFile>) 메서드가 그 역할을 대신합니다.
    // 💡 주석처리된 예전 getList() 메서드도 제거했습니다. boardList() 메서드가 그 역할을 대신합니다.
}