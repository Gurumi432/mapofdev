package dev.mapofdev.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Swagger API 문서화 설정 클래스
 *
 * mapofdev 프로젝트의 REST API 문서를 자동으로 생성하고
 * 개발자들이 쉽게 API를 테스트하고 이해할 수 있도록
 * OpenAPI 3.0 기반의 Swagger UI 환경을 구성하는 설정 클래스입니다.
 *
 * 접속 URL: http://localhost:8080/swagger-ui/index.html
 */
@Configuration // 스프링 설정 클래스로 등록
public class SwaggerConfig {

  @Value("${server.port:8080}") // application.yml에서 포트 값 읽기 (기본값: 8080)
  private String serverPort; // 서버 포트 설정 (동적으로 변경 가능)

  /**
   * OpenAPI 설정 Bean 생성
   *
   * Swagger UI에서 표시될 API 문서의 전체적인 구성을 정의합니다.
   * API 정보, 서버 정보, 보안 설정 등을 포함합니다.
   */
  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
      .info(apiInfo()) // API 기본 정보 설정 (제목, 설명, 버전 등)
      .servers(getServers()) // 테스트 가능한 서버 목록 설정
      .components(new Components() // API 컴포넌트 설정
        .addSecuritySchemes("bearerAuth", // JWT 인증 방식 정의
          new SecurityScheme()
            .type(SecurityScheme.Type.HTTP) // HTTP 인증 타입
            .scheme("bearer") // Bearer 토큰 방식
            .bearerFormat("JWT"))) // JWT 형식 명시
      .security(List.of(new SecurityRequirement().addList("bearerAuth"))); // 전역 보안 요구사항 설정
  }

  /**
   * API 기본 정보 설정
   *
   * Swagger UI 상단에 표시될 API 문서의 메타데이터를
   * 정의합니다 (제목, 설명, 버전, 연락처, 라이선스 등).
   */
  private Info apiInfo() {
    return new Info()
      .title("MapOfDev API") // API 문서 제목
      .description("데이터로 커리어를 설계하는 개발자 플랫폼 API") // API 설명
      .version("1.0.0") // API 버전
      .contact(new Contact() // 개발팀 연락처 정보
        .name("MapOfDev Team") // 팀명
        .email("support@mapofdev.com") // 지원 이메일
        .url("https://mapofdev.com")) // 프로젝트 홈페이지
      .license(new License() // 라이선스 정보
        .name("Apache 2.0") // 라이선스명
        .url("https://www.apache.org/licenses/LICENSE-2.0")); // 라이선스 URL
  }

  /**
   * API 테스트 서버 목록 설정
   *
   * Swagger UI에서 "Try it out" 기능을 사용할 때
   * 요청을 보낼 서버들의 목록을 정의합니다.
   * 개발 환경과 운영 환경을 구분하여 설정 가능합니다.
   */
  private List<Server> getServers() {
    // 로컬 개발 서버 설정 (포트 동적 변경 가능)
    Server localServer = new Server()
      .url("http://localhost:" + serverPort) // 현재 실행 중인 포트 사용
      .description("로컬 개발 서버"); // 서버 설명

    // 개발 서버 설정 (향후 배포 시 사용)
    Server devServer = new Server()
      .url("https://dev-api.mapofdev.com") // 개발 서버 URL
      .description("개발 서버"); // 서버 설명

    return List.of(localServer, devServer); // 서버 목록 반환 (로컬 → 개발 순서)
  }
}
