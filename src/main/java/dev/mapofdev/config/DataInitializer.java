  package dev.mapofdev.config;

  import lombok.extern.slf4j.Slf4j;
  import org.springframework.boot.CommandLineRunner;
  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.Configuration;
  import org.springframework.context.annotation.Profile;

  /*
Spring 서버는 애플리케이션 실행 시점에, 운영 환경이 아닐 경우에만,
초기 데이터를 설정하는 로직을 담은 CommandLineRunner 객체를 생성해 Bean으로 등록하고 자동 실행한다
   */

  @Slf4j
  @Configuration
  public class DataInitializer {

    @Bean
    @Profile("!prod")  // 운영 환경에서는 실행 안됨
    CommandLineRunner init() {
      return args -> {
        log.info("=== 초기 데이터 설정 시작 ===");

        // TODO: 초기 데이터 생성 로직

        log.info("=== 초기 데이터 설정 완료 ===");
      };
    }
  }
