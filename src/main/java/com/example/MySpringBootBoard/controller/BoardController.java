package com.example.MySpringBootBoard.controller;

import com.example.MySpringBootBoard.entity.Board;
import com.example.MySpringBootBoard.service.BoardService;
// import lombok.RequiredArgsConstructor; // 이제 @RequiredArgsConstructor가 필요 없으면 주석 처리하거나 지워도 돼!
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

// @RequiredArgsConstructor // 수동 생성자를 만들 거니, 이 어노테이션은 없거나 주석 처리해도 돼!
@Controller
public class BoardController { // 파일 이름도 BoardController.java로 변경했다고 가정!

    private final BoardService boardService; // 이 줄에 오류가 나고 있지?

    // ⭐⭐⭐⭐ 수동으로 생성자를 만들어 주입하는 코드 ⭐⭐⭐⭐
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }
    // ⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐

    // 게시글 목록 페이지를 처리하는 메서드
    @GetMapping("/board/list")
    public String list(Model model) {
        List<Board> boardList = this.boardService.getList();
        model.addAttribute("boardList", boardList);
        return "board_list";
    }
    // ⭐⭐⭐⭐ 게시글 작성 폼을 보여주는 메서드 추가 ⭐⭐⭐⭐
    @GetMapping("/board/write") // /board/write 경로로 GET 요청이 오면 (브라우저에서 직접 접속하거나 링크 클릭 시) 이 메서드가 실행
    public String writeForm() {
        return "board_form"; // src/main/resources/templates/board_form.html 파일을 찾아서 브라우저에 보여주라는 뜻
    }
    // ⭐⭐⭐⭐ 새롭게 추가: 게시글 작성 폼으로부터 POST 요청을 받아 처리하는 메서드 ⭐⭐⭐⭐
    @PostMapping("/board/write")
    public String write(@RequestParam("title") String title,
                        @RequestParam("content") String content,
                        @RequestParam("author") String author) {
        // BoardService의 saveBoard 메서드를 호출하여 데이터베이스에 게시글 저장
        this.boardService.saveBoard(title, content, author);
        // 게시글 저장이 완료되면 게시글 목록 페이지로 리다이렉트
        return "redirect:/board/list";
    }

}