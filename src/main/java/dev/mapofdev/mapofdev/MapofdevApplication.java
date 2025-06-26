package dev.mapofdev.mapofdev; // 패키지 경로 정의 (폴더 구조와 일치)

import org.springframework.boot.SpringApplication; // 스프링부트 앱 실행 도구

// 복합 어노테이션, 앱의 시작점(Spring Boot의 진입점) + 필요한 설정 자동 구성 + 컴포넌트 자동 스캔
import org.springframework.boot.autoconfigure.SpringBootApplication; 
//REST란? REpresentational State Transfer, 웹에서 리소스(자원) 를 URL로 표현하고, HTTP 메서드(GET, POST 등)를 써서 이 자원을 조회·생성·수정·삭제하는 방식
//이노테이션이란? 코드에 붙이는 부가적인 명령어 (컴파일러, 프레임워크, API 등등에 대한 명령)
//프레임워크는 "코드를 짜는 틀"
//API는 "코드를 통해 쓰는 기능집"

@SpringBootApplication // 복합 어노테이션, 앱의 시작점(Spring Boot의 진입점) + 필요한 설정 자동 구성 + 컴포넌트 자동 스캔

public class MapofdevApplication { //Spring Boot 앱을 실행시키는 "시동 버튼" 역할 클래스

public static void main(String[] args) {                      // 자바 애플리케이션의 진입점(시작 메서드)
    SpringApplication.run(MapofdevApplication.class, args);   // Spring Boot 앱 실행 → 내장 톰캣 서버가 자동으로 시작됨
}

// ────────────── 컨트롤러 메서드 (REST API) ──────────────
// 컨트롤 메서드란? 사용자가 웹 요청(예: 브라우저 주소창에 URL 입력)을 보냈을 때, 그 요청을 처리하는 자바 메서드를 말해요.

@GetMapping("/hello")                         // ① HTTP GET 방식 & URL /hello 로 들어온 요청에만 반응
//HTTP Get 방식이란? 클라이언트가 서버로부터 데이터를 조회(fetch) 하기 위해 사용하는 안전한 read-only HTTP 요청 방식이다.
public String hello(
        @RequestParam(                        // ② 쿼리 스트링에서 값을 꺼내 메서드 인자로 전달
                value = "name",               //    └ ?name=진남 → "value" 자리에 매칭
                required = false,             //    └ 파라미터가 없어도 400 오류 내지 않음
                defaultValue = "World")       //    └ 비어 있으면 자동으로 **"World"** 사용
        String name                           // ③ 최종적으로 매개변수 **name** 에 대입
) {
    // ④ 반환 문자열 그대로 HTTP 응답 본문(body)으로 전송
    //    예) /hello?name=진남 → "Hello 진남!"
    //    예) /hello           → "Hello World!"
    return "Hello " + name + "!";
}
// ───────────────────────────────────────────────────────

}
