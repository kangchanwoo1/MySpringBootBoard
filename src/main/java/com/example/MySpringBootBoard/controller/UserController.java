package com.example.MySpringBootBoard.controller;

import com.example.MySpringBootBoard.entity.User; // User 엔티티 임포트
import com.example.MySpringBootBoard.service.UserService; // UserService 임포트
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; // 컨트롤러 어노테이션
import org.springframework.web.bind.annotation.GetMapping; // GET 요청 처리 어노테이션
import org.springframework.web.bind.annotation.PostMapping; // POST 요청 처리 어노테이션
import org.springframework.web.bind.annotation.RequestMapping; // 공통 URL 경로 설정 어노테이션
import org.springframework.ui.Model; // 뷰로 데이터 전달을 위한 Model 객체 임포트


@Controller // 이 클래스가 Spring MVC의 컨트롤러임을 나타냄
@RequestMapping("/user") // "/user"로 시작하는 모든 요청을 이 컨트롤러에서 처리
public class UserController {

    private final UserService userService; // UserService를 주입받음

    // 생성자 주입 방식으로 UserService를 주입받음
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ⭐ 1. 회원가입 폼을 보여주는 GET 요청 처리 ⭐
    @GetMapping("/join") // GET 요청: /user/join
    public String joinForm(Model model) {
        model.addAttribute("user", new User()); // 빈 User 객체를 폼에 바인딩하기 위해 전달
        return "user_join_form"; // src/main/resources/templates/user_join_form.html 파일을 렌더링
    }

    // ⭐ 2. 회원가입 폼에서 제출된 데이터를 처리하는 POST 요청 처리 ⭐
    @PostMapping("/join") // POST 요청: /user/join
    public String join(User user, Model model) {
        try {
            // UserService를 통해 회원가입 로직을 수행합니다.
            userService.join(user);
            // 회원가입 성공 시 로그인 페이지로 리다이렉트 (추후 로그인 페이지 구현 후)
            // 지금은 편의상 다시 회원가입 폼으로 리다이렉트
            return "redirect:/user/login"; // 로그인 폼으로 리다이렉트 (나중에 구현)
                                          // 현재는 임시로 /user/joinForm 페이지로 리다이렉트한다고 가정 (나중에 login 페이지로 변경)
        } catch (IllegalStateException e) {
            // 중복 아이디, 이메일, 핸드폰 번호 등 회원가입 실패 시 오류 메시지를 모델에 담아 다시 폼으로 전달
            model.addAttribute("user", user); // 사용자가 입력했던 데이터 유지
            model.addAttribute("errorMessage", e.getMessage()); // 오류 메시지 전달
            return "user_join_form"; // 회원가입 폼 페이지를 다시 렌더링
        }
    }

    // ⭐ 3. 로그인 폼을 보여주는 GET 요청 처리 (추후 구현) ⭐
    @GetMapping("/login")
    public String loginForm() {
        return "user_login_form"; // src/main/resources/templates/user_login_form.html 렌더링 (나중에 만들자!)
    }
}