package com.example.MySpringBootBoard.controller;

import com.example.MySpringBootBoard.entity.Board;
import com.example.MySpringBootBoard.service.BoardService;
// import lombok.RequiredArgsConstructor; // 이제 @RequiredArgsConstructor가 필요 없으면 주석 처리하거나 지워도 돼!
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
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
 // 3. 게시글 상세 페이지 보여주기 (⚠️ 이 부분을 확인해 줘!)
    @GetMapping("/board/detail/{id}")
    public String getBoard(@PathVariable("id") Integer id, Model model) {
        // boardService.getBoard(id) 가 제대로 데이터를 가져오는지도 중요해!
        // 만약 데이터가 없으면 Thymeleaf 템플릿 처리 중에 에러가 날 수도 있어.
        model.addAttribute("board", boardService.getBoard(id));
        return "board_detail"; // 💡 여기서 "board_detail" 이 정확한지, 오타는 없는지 확인!
    }
 // 💡 5. 게시글 수정 처리하는 POST 요청 처리
    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board) {
        // 경로 변수로 받은 id가 board 객체의 id와 다를 경우를 대비해 설정
        board.setId(id);
        
        // boardService의 업데이트 메서드 호출
        boardService.updateBoard(board);
        
        // 수정 완료 후 해당 게시글의 상세 페이지로 리다이렉트
        return "redirect:/board/detail/" + board.getId();
    }
    @GetMapping("/board/modify/{id}")
    public String boardModifyForm(@PathVariable("id") Integer id, Model model) {
        // 💡 디버깅 코드 시작!
        System.out.println("DEBUG: boardModifyForm 메서드 진입 - 요청 ID: " + id);

        Board board = boardService.getBoard(id); // 서비스로부터 게시글 가져오기

        if (board == null) { // 💡 게시글이 null인지 확인
            System.out.println("DEBUG: boardService.getBoard(" + id + ") 결과, 게시글을 찾을 수 없습니다 (null).");
            // TODO: 에러 페이지 또는 메시지 처리 로직 추가 필요
            // 임시로 목록으로 리다이렉트 (실제 운영 시에는 404 페이지나 적절한 에러 처리 필요)
            return "redirect:/board/list";
        }
        System.out.println("DEBUG: boardService.getBoard(" + id + ") 결과 - 제목: " + board.getTitle());

        model.addAttribute("board", board); // 찾은 게시글을 모델에 추가
        System.out.println("DEBUG: 모델에 'board' 객체 추가 완료. board_modify.html로 이동합니다.");
        // 💡 디버깅 코드 끝!

        return "board_modify"; // board_modify.html 템플릿을 찾아라!
    }
    @GetMapping("/board/delete/{id}")
    public String boardDelete(@PathVariable("id") Integer id) {
        System.out.println("DEBUG: boardDelete 메서드 진입 - 삭제 요청 ID: " + id); // 디버그 추가
        boardService.deleteBoard(id); // boardService의 삭제 메서드 호출
        System.out.println("DEBUG: boardDelete - 게시글 ID " + id + " 삭제 처리 완료."); // 디버그 추가

        // 삭제 완료 후 게시글 목록 페이지로 리다이렉트
        return "redirect:/board/list";
    }
}