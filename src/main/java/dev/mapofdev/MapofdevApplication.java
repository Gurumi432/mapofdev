package dev.mapofdev; // 패키지 경로 정의 (폴더 구조와 일치)

import org.springframework.boot.SpringApplication; // 스프링부트 앱 실행 도구
import org.springframework.boot.autoconfigure.SpringBootApplication; 
// 복합 어노테이션 임포트, 앱의 시작점(Spring Boot의 진입점) + 필요한 설정 자동 구성 + 컴포넌트 자동 스캔
//이노테이션이란? 코드에 붙이는 부가적인 명령어 (컴파일러, 프레임워크, API 등등에 대한 선언 혹은 명령)
//프레임워크는 "코드를 짜는 틀"
//API는 "코드를 통해 쓰는 기능집"

@SpringBootApplication // 이 클래스 쓰겠다고 선언한거 (선언 전에 임포트 해야함)

public class MapofdevApplication { //Spring Boot 앱을 실행시키는 "시동 버튼" 역할 클래스

public static void main(String[] args) {                      // 자바 애플리케이션의 진입점(시작 메서드)
    SpringApplication.run(MapofdevApplication.class, args);   // Spring Boot 앱 실행 → 내장 톰캣 서버가 자동으로 시작됨
}


}
