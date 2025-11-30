package com.example.MySpringBootBoard.controller; // ⭐⭐ 여기! 대소문자 정확히 맞춰줘! ⭐⭐

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/")
    public String hello(Model model) {
        model.addAttribute("message", "🌟 찬우의 첫 Spring Boot 웹 페이지 🌟");
        return "hello"; // resources/templates/hello.html 을 찾을 거야!
    }

    @GetMapping("/hello")
    public String helloCustom(Model model) {
        model.addAttribute("message", "🔥 Spring Boot, 이번엔 성공이다! 🔥");
        return "hello";
    }
}