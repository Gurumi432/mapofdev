package dev.mapofdev.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 이 클래스가 설정 클래스임을 명시
@EnableWebSecurity // Spring Security를 활성화함
public class SecurityConfig {

  /*
   Spring 서버는 모든 HTTP 요청에 대해, 클라이언트가 로그인 여부와 관계없이
   자유롭게 접근할 수 있도록 허용하며, CSRF 보호 기능은 비활성화하고,
   H2 콘솔을 위한 frameOptions 헤더 설정을 sameOrigin으로 지정하는 보안 정책을 적용한다.
   (frameOptions: 웹페이지가 <iframe>으로 로드될 수 있는지 제어하는 보안 헤더다. sameOrigin: 현재 페이지)

   또한, 이 보안 정책을 기반으로 SecurityFilterChain 객체를 생성해 Spring
   컨테이너에 등록하여, 실행 시 이 설정이 자동으로 반영되도록 구성한다.
  */

  @Bean // 이 메서드의 반환 객체를 스프링 빈으로 등록함
  //빈 객체(Bean): Spring이 직접 만들어서 관리하는 객체를 말한다.
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화 (개발 단계에서는 편의상 끔)
      // CSRF: 로그인된 사용자의 권한을 훔쳐서 원하지 않는 요청을 강제로 보내는 공격을 막기 위한 보안 기능이다.

      .authorizeHttpRequests(authz -> authz
        .anyRequest() // 모든 요청에 대해
        .permitAll()  // 인증 없이 허용
      )
      .headers(headers ->
        headers.frameOptions(frameOptions -> frameOptions.sameOrigin()) // H2 콘솔 접근 허용
      );

    return http.build(); // 설정을 적용한 필터 체인 객체 생성
  }
}
