package com.example.MySpringBootBoard.controller;

import com.example.MySpringBootBoard.entity.Board; 
// 해당 클래스에서는 사용자의 요청을 처리하면서 Board객체를 생성하거나 BoardService로부터 Board 객체를 받아서(예: 게시글 조회/수정) Model에 담아 View로 보내야함. 
// Board라는 클래스를 여기서 사용하려면 이 클래스의 위치를 정확히 알려주기 위함

import com.example.MySpringBootBoard.service.BoardService;
// 해당 클래스는 웹 요청을 받아 적절한 Service에게 작업을 위임하는 역할만 함. 
// 그래서 BoardService 객체를 주입받음(@Autowired 사용) BoardService 클래스에 게시글 목록, 게시글 DB에 저장 등으로 호출해야하니 BoardService 위치를 명시

import org.springframework.stereotype.Controller;
// @Controller를 사용하기 위함이며, 스프링프레임워크가 애플리케이션을 실행하면서 @Controller가 붙은 클래스들을 찾아 웹요청을 처리하는 컨트롤러라는것을 명시

import org.springframework.ui.Model;
// Controller에서 처리한 결과데이터(예: 게시글 목록 List<Board>, 특정 Board 객체)를 웹 브라우저에 보여줄 HTML 템플릿(뷰)으로 넘겨줘야 할 때 Model객체를 사용.
// addAttribute() 메서드를 사용해서 데이터를 담으면 board_list.html이나 board_detail.html 같은 Thymeleaf 템플릿에서 이 데이터를 사용가능

import org.springframework.web.bind.annotation.GetMapping;
// 웹 요청 중 HTTP GET 요청(주로 데이터를 조회하거나 페이지를 보여줄 때 사용)을 특정 메서드에 연결할 때 사용
// EX : BoardController의 boardList() 메서드 위에 @GetMapping("/board/list")라고 붙여두면, /board/list로 들어오는 GET 요청은 모두 boardList() 메서드가 처리

import org.springframework.web.bind.annotation.PostMapping;
// 웹 요청 중 HTTP POST 요청(주로 데이터를 서버로 전송하거나 생성, 수정할 때 사용)을 특정 메서드에 연결할 때 사용
// EX : 게시글 작성 폼에서 '작성 완료' 버튼을 누르면 POST 요청이 발생하는데, 이때 @PostMapping("/board/writepro")가 붙은 boardWritePro() 메서드가 이 요청을 처리

import org.springframework.web.bind.annotation.RequestParam;
// 웹 요청 URL의 쿼리 파라미터(예: ?page=1&size=10)나 폼 데이터의 특정 필드 값을 메서드의 인자(매개변수)로 받아올 때 사용
// RequestParam은 URL 뒤에 붙는 ? 뒤의 파라미터를 받음

import org.springframework.web.bind.annotation.PathVariable;
// URL 경로의 일부를 변수로 받아올 때 사용
// EX : 게시글 상세 페이지를 http://localhost:8080/board/detail/123 이런 식으로 특정 ID(123)를 경로에 직접 포함시켜 요청할 때가 있다면
// 이때 @GetMapping("/board/detail/{id}") 메서드의 인자에 @PathVariable("id") Integer id라고 선언하면, URL의 123이라는 값을 id 변수로 편하게 받아올 수 있음

import java.util.List;
// BoardService.boardList() 메서드가 여러 개의 게시글 객체(Board 객체들)를 한 번에 반환할 때, 이 객체들을 List<Board>라는 형태로 묶어서 전달
// Model에 List 형태의 데이터를 담아서 View로 전달할 때도 사용
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class BoardController {

    // ⭐⭐⭐⭐ @Autowired 필드 주입 방식 사용 (Lombok 문제 우회) ⭐⭐⭐⭐
    @Autowired
    private BoardService boardService; // final 키워드는 제거!


    // 1. 게시글 목록 페이지 보여주기
    @GetMapping("/board/list")
    public String boardList(Model model) { // 메서드 이름은 boardList, 파라미터는 Model
        List<Board> boardList = this.boardService.boardList(); // getList()가 아닌 boardList() 호출
        model.addAttribute("boardList", boardList); // "boardList"라는 이름으로 View에 전달
        return "board_list"; // board_list.html 템플릿 반환
    }

    // 2. 게시글 작성 폼 페이지 보여주기
    @GetMapping("/board/write") // GET 요청으로 /board/write에 접속
    public String writeForm() {
        return "board_form"; // board_form.html 템플릿 반환
    }

    // ⭐⭐⭐⭐ 3. 게시글 저장 처리 (파일 업로드 포함) ⭐⭐⭐⭐
    @PostMapping("/board/writepro") // POST 요청으로 /board/writepro에 접속
    public String boardWritePro(Board board, // 폼에서 넘어온 title, content, author를 Board 객체로 자동 바인딩
                                @RequestParam("files") List<MultipartFile> files) throws IOException { // "files" 이름의 파일 리스트를 받음
        // System.out.println("DEBUG: 게시글 작성 및 파일 업로드 처리 시작"); // 디버그 로그는 최종 코드에서 제거
        boardService.write(board, files); // Service 계층에 Board 객체와 파일 리스트 전달
        // System.out.println("DEBUG: 게시글 및 파일 저장 완료. 목록으로 리다이렉트."); // 디버그 로그 제거
        return "redirect:/board/list"; // 게시글 목록 페이지로 리다이렉트
    }

    // 4. 특정 ID의 게시글 상세 페이지 보여주기
    @GetMapping("/board/detail/{id}") // GET 요청으로 /board/detail/{id}에 접속
    public String getBoard(@PathVariable("id") Integer id, Model model) {
        // System.out.println("DEBUG: getBoard 메서드 진입 - 요청 ID: " + id); // 디버그 로그 제거
        Board board = boardService.getBoard(id); // BoardService로부터 게시글 가져오기
        if (board == null) {
            // System.out.println("DEBUG: 게시글을 찾을 수 없습니다 (ID: " + id + ")."); // 디버그 로그 제거
            return "redirect:/board/list"; // 게시글이 없으면 목록으로 리다이렉트
        }
        // System.out.println("DEBUG: 게시글 발견 - 제목: " + board.getTitle()); // 디버그 로그 제거
        model.addAttribute("board", board); // 찾은 게시글을 모델에 추가
        return "board_detail"; // board_detail.html 템플릿 반환
    }

    // ⭐⭐⭐⭐ 5. 게시글 수정 폼 페이지 보여주기 ⭐⭐⭐⭐
    @GetMapping("/board/modify/{id}") // GET 요청으로 /board/modify/{id}에 접속
    public String boardModifyForm(@PathVariable("id") Integer id, Model model) {
        // System.out.println("DEBUG: boardModifyForm 메서드 진입 - 요청 ID: " + id); // 디버그 로그 제거
        Board board = boardService.getBoard(id); // 서비스로부터 게시글 가져오기
        if (board == null) {
            // System.out.println("DEBUG: 게시글을 찾을 수 없습니다 (ID: " + id + ")."); // 디버그 로그 제거
            return "redirect:/board/list";
        }
        // System.out.println("DEBUG: 모델에 'board' 객체 추가 완료. board_form.html로 이동합니다."); // 디버그 로그 제거
        model.addAttribute("board", board); // 찾은 게시글을 모델에 추가
        return "board_form"; // board_form.html (작성/수정 공통 폼) 템플릿 반환
    }

    // ⭐⭐⭐⭐ 6. 게시글 수정 처리 ⭐⭐⭐⭐
    @PostMapping("/board/update/{id}") // POST 요청으로 /board/update/{id}에 접속
    public String boardUpdate(@PathVariable("id") Integer id, Board board) {
        board.setId(id); // URL 경로에서 받은 ID를 Board 객체에 설정 (안전성 확보)
        boardService.updateBoard(board); // BoardService의 업데이트 메서드 호출
        return "redirect:/board/detail/" + board.getId(); // 수정 후 상세 페이지로 리다이렉트
    }

    // ⭐⭐⭐⭐ 7. 게시글 삭제 처리 ⭐⭐⭐⭐
    @GetMapping("/board/delete/{id}") // GET 요청으로 /board/delete/{id}에 접속
    public String boardDelete(@PathVariable("id") Integer id) {
        // System.out.println("DEBUG: boardDelete 메서드 진입 - 삭제 요청 ID: " + id); // 디버그 로그 제거
        boardService.deleteBoard(id); // BoardService의 삭제 메서드 호출
        // System.out.println("DEBUG: 게시글 ID " + id + " 삭제 처리 완료."); // 디버그 로그 제거
        return "redirect:/board/list"; // 삭제 후 목록 페이지로 리다이렉트
    }
}