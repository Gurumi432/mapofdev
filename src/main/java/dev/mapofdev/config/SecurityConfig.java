package dev.mapofdev.config; // 패키지 경로 정의 (폴더 구조와 일치)

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests((authz) -> authz
                .requestMatchers("/hello").permitAll()  // /hello는 로그인 없이 허용
                .anyRequest().authenticated()
            )
            .formLogin(); // 기본 로그인 페이지 유지
        return http.build();
    }
}
