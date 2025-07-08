package dev.mapofdev.exception;

import dev.mapofdev.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 전역 예외 처리 핸들러
 *
 * mapofdev 프로젝트에서 발생하는 모든 예외를 중앙에서 처리하여
 * 일관된 에러 응답 형식을 제공하고 적절한 HTTP 상태 코드와 함께
 * 사용자 친화적인 오류 메시지를 반환하는 글로벌 예외 처리 클래스입니다.
 *
 * ⚠️ Actuator 엔드포인트(/actuator/*)는 제외하고 처리합니다.
 */
@Slf4j // 로깅 기능 자동 생성
@RestControllerAdvice // 모든 컨트롤러의 예외를 처리하는 전역 핸들러
public class GlobalExceptionHandler {

  /**
   * 일반적인 예외 처리
   *
   * 예상하지 못한 모든 예외를 포괄적으로 처리하여
   * 시스템이 중단되지 않도록 하고 500 Internal Server Error로
   * 안전한 에러 메시지를 사용자에게 제공합니다.
   *
   * 📍 중요: Actuator 경로는 Spring Boot가 직접 처리하도록 제외
   */
  @ExceptionHandler(Exception.class) // 모든 예외 타입을 처리
  public ResponseEntity<ApiResponse<Void>> handleException(Exception e, HttpServletRequest request) {

    // 🚫 Actuator 경로 체크 - Spring Boot가 처리하도록 넘김
    String requestURI = request.getRequestURI();
    if (requestURI.startsWith("/actuator/")) {
      log.debug("Actuator 경로 요청은 Spring Boot가 처리: {}", requestURI);
      // 원래 예외를 다시 던져서 Spring Boot의 기본 처리기가 처리하게 함
      if (e instanceof RuntimeException) {
        throw (RuntimeException) e;
      } else {
        throw new RuntimeException(e);
      }
    }

    log.error("예외 발생", e); // 개발자용 상세 로그 기록
    return ResponseEntity
      .status(HttpStatus.INTERNAL_SERVER_ERROR) // 500 상태 코드
      .body(ApiResponse.fail("서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.")); // 사용자 친화적 메시지
  }

  /**
   * 잘못된 요청 파라미터 처리
   *
   * URL 경로나 요청 파라미터의 타입이 예상과 다를 때 발생하는 예외를 처리합니다.
   * 예: "/api/trends/category/123" (숫자)를 String으로 받으려 할 때
   */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class) // 파라미터 타입 불일치 예외 처리
  public ResponseEntity<ApiResponse<Void>> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
    String message = String.format("잘못된 파라미터 타입: %s", e.getName()); // 구체적인 파라미터명 포함
    log.warn(message); // 경고 수준 로그 (에러보다 낮은 레벨)
    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST) // 400 Bad Request 상태 코드
      .body(ApiResponse.fail(message));
  }

  /**
   * 유효성 검증 실패 처리
   *
   * @Valid 어노테이션이 붙은 DTO의 필드 검증이 실패했을 때 처리합니다.
   * 각 필드별로 어떤 검증이 실패했는지 상세하게 클라이언트에게 알려줍니다.
   * 예: 이메일 형식 오류, 필수값 누락, 길이 제한 초과 등
   */
  @ExceptionHandler(MethodArgumentNotValidException.class) // 유효성 검증 실패 예외 처리
  public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(
    MethodArgumentNotValidException e) {
    Map<String, String> errors = new HashMap<>(); // 필드명과 에러 메시지를 저장할 맵
    e.getBindingResult().getAllErrors().forEach((error) -> { // 모든 검증 에러를 순회
      String fieldName = ((FieldError) error).getField(); // 실패한 필드명 추출
      String errorMessage = error.getDefaultMessage(); // 에러 메시지 추출
      errors.put(fieldName, errorMessage); // 맵에 필드별 에러 정보 저장
    });

    log.warn("유효성 검증 실패: {}", errors); // 검증 실패 상세 내용 로그 기록
    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST) // 400 Bad Request 상태 코드
      .body(ApiResponse.<Map<String, String>>builder() // 제네릭 타입 명시적 지정
        .success(false)
        .message("입력값 검증에 실패했습니다.")
        .data(errors) // 필드별 에러 정보를 데이터로 포함
        .build());
  }

  /**
   * 리소스를 찾을 수 없음 처리
   *
   * 요청된 데이터가 데이터베이스에 존재하지 않을 때 발생하는
   * 커스텀 예외를 처리합니다. 404 Not Found 상태 코드와 함께
   * 구체적인 리소스 정보를 포함한 메시지를 반환합니다.
   */
  @ExceptionHandler(ResourceNotFoundException.class) // 커스텀 예외 클래스 처리
  public ResponseEntity<ApiResponse<Void>> handleResourceNotFound(ResourceNotFoundException e) {
    log.warn("리소스를 찾을 수 없음: {}", e.getMessage()); // 찾을 수 없는 리소스 정보 로그
    return ResponseEntity
      .status(HttpStatus.NOT_FOUND) // 404 Not Found 상태 코드
      .body(ApiResponse.fail(e.getMessage())); // 예외에서 정의한 메시지 사용
  }

  /**
   * 비즈니스 로직 예외 처리
   *
   * 애플리케이션의 비즈니스 규칙 위반 시 발생하는 커스텀 예외를 처리합니다.
   * 예: 중복 데이터 생성 시도, 권한 없는 작업 시도, 잘못된 상태 변경 등
   * 클라이언트의 잘못된 요청으로 간주하여 400 Bad Request로 응답합니다.
   */
  @ExceptionHandler(BusinessException.class) // 비즈니스 로직 예외 처리
  public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException e) {
    log.warn("비즈니스 예외: {}", e.getMessage()); // 비즈니스 규칙 위반 상황 로그
    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST) // 400 Bad Request 상태 코드
      .body(ApiResponse.fail(e.getMessage())); // 예외에서 정의한 구체적 메시지 사용
  }
}
