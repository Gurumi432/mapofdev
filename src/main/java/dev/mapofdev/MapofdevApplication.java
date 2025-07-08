package dev.mapofdev; // 패키지 경로 정의 (폴더 구조와 일치)

import org.springframework.boot.SpringApplication; // 스프링부트 앱 실행 도구
import org.springframework.boot.autoconfigure.SpringBootApplication;
// 복합 Annotaition에 대한  import(앱의 실행 + 필요한 설정 자동 구성 + 컴포넌트 자동 스캔)

// ✅ Redis 자동 구성 제외를 위한 import 추가
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;

/*Annotation은 자바 코드에 @ 기호를 이용해 부착되는 metadata(데이터를 설명하는 데이터)`로,
컴파일러, 빌드 도구, 런타임 환경 또는 프레임워크가 해당 코드에 특별한 의미나 동작을 부여하도록 하는 기법이다.
예: @RequestParam, @Override, @GetMapping 등 */

//프레임워크는 "코드를 짜는 틀"
//API는 "코드를 통해 쓰는 기능집"

// ✅ Redis 자동 구성을 제외하도록 설정 추가
@SpringBootApplication(exclude = {
  RedisAutoConfiguration.class,          // Redis 기본 설정 제외
  RedisRepositoriesAutoConfiguration.class  // Redis Repository 설정 제외
}) // 스프링 부트 애플리케이션의 시작점(main class)으로 지정하면서 Redis는 사용하지 않도록 설정
public class MapofdevApplication { //Spring Boot 앱을 실행시키는 "시동 버튼" 역할 클래스

  public static void main(String[] args) {                      // 자바 애플리케이션의 진입점(시작 메서드)
    SpringApplication.run(MapofdevApplication.class, args);   // Spring Boot 앱 실행 → 내장 톰캣 서버가 자동으로 시작됨
  }

}
