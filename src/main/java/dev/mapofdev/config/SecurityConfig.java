package dev.mapofdev.config; // 이 클래스가 위치한 패키지 경로 (스프링이 자동으로 인식하려면 메인 패키지 하위여야 함)

import org.springframework.context.annotation.Bean; // @Bean 어노테이션을 쓰기 위해 필요 (스프링 컨테이너에 객체 등록용)
import org.springframework.context.annotation.Configuration; // 이 클래스가 설정 클래스임을 나타냄
import org.springframework.security.config.annotation.web.builders.HttpSecurity; // 보안 설정을 구성할 때 사용되는 빌더 클래스
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; // Spring Security를 활성화하는 어노테이션
import org.springframework.security.web.SecurityFilterChain; // 시큐리티 필터 체인을 직접 구성할 때 사용하는 클래스

@Configuration // 설정
@EnableWebSecurity // 스프링 보안
public class SecurityConfig {

  @Bean //@Bean은 개발자가 직접 정의한 메서드의 리턴값을, 스프링 컨테이너의 Bean으로 등록
//스프링 컨테이너는 애플리케이션에서 사용하는 객체(Bean)를 생성하고, 조립하고, 관리하는 중심 컴포넌트
//Bean은 스프링 컨테이너가 생성했거나 관리하는 객체
// HTTP 보안 정책을 정의하는 메서드. 최종적으로 SecurityFilterChain 객체를 반환함
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { // HTTP 보안 정책을 정의하는 메서드
    http.csrf(csrf -> csrf.disable())   // CSRF 보호 기능을 비활성화함 (개발 시에는 편의상 꺼두지만, 운영에서는 보통 켜둠)
            // CSRF 보호는 사용자가 원하지도 않았는데, 로그인된 상태를 악용해서 악의적인 요청을 강제로 보내는 공격을 막는 기능
            .authorizeHttpRequests(authz -> authz   // HTTP 요청에 대한 인가(접근 권한) 설정을 시작
                    .anyRequest()                           // 모든 요청에 대해 (개발 단계이므로 전체 허용으로 변경)
                    .permitAll()                            // 인증 여부와 상관없이 누구나 접근 허용 (로그인 필요 없음)
            )
            // ✅ Spring Security 6.x 최신 방식으로 H2 콘솔용 헤더 설정
            .headers(headers ->
                    headers.frameOptions(frameOptions -> frameOptions.sameOrigin())
            );

    return http.build(); // 설정한 보안 정책을 바탕으로 SecurityFilterChain 객체를 생성하여 반환
  }

}
