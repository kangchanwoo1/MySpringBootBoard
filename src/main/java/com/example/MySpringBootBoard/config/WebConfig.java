package com.example.MySpringBootBoard.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.dir}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // "/files/**" URL 요청이 오면
        // file:///{실제_업로드_경로}/ 에서 리소스를 찾음
        registry.addResourceHandler("/files/**") // 💡 웹에서 "/files/어떤파일.jpg" 로 요청이 오면
                .addResourceLocations("file:///" + uploadPath); // 💡 {실제 파일 저장 경로}/어떤파일.jpg 에서 찾아라!
    }
}