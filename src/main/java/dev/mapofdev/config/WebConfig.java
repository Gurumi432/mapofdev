package dev.mapofdev.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration  // 설정 클래스임을 명시
public class WebConfig implements WebMvcConfigurer {  // Spring 웹 설정을 바꾸기 위해 인터페이스 구현

  @Override
  public void addCorsMappings(CorsRegistry registry) {

/*
Spring 서버는 /api/** 경로에 대해, 클라이언트가 localhost:3000, localhost:5173,
localhost:8081에서 GET, POST, PUT, DELETE, OPTIONS 요청을 보낼 경우,
모든 요청 헤더를 허용하고, 응답 헤더 중 Authorization을 노출하며, 이 설정을 3600초간 유지한다.
또한, Spring 서버는 클라이언트가 쿠키나 인증 토큰 등의 자격 증명 정보를 포함한 요청을 보낼 수 있도록 허용한다
*/

    registry.addMapping("/**") // /api로 시작하는 요청에 대해

      .allowedOrigins(
        "http://localhost:3000",  // 프론트엔드 개발 서버 주소들
        "http://localhost:5173",
        "http://localhost:8081"
      )

      .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메서드

      .allowedHeaders("*")  // 어떤 요청 헤더든 허용

      .exposedHeaders("Authorization") // 응답에 포함된 Authorization 헤더 읽기 허용

      .allowCredentials(true) // 쿠키 같은 정보 포함 허용

      .maxAge(3600); // 설정 결과를 1시간 동안 캐시함
  }
}
