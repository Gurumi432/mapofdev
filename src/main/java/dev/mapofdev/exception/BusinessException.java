package dev.mapofdev.exception;

/**
 * 비즈니스 로직 예외
 *
 * 애플리케이션의 비즈니스 규칙 위반이나 도메인 로직 오류 시
 * 사용하는 커스텀 예외 클래스입니다.
 */
@SuppressWarnings("unused") // 사용되지 않는 생성자에 대한 경고 억제
public class BusinessException extends RuntimeException {

  /**
   * 기본 생성자
   */
  public BusinessException() {
    super("비즈니스 로직 처리 중 오류가 발생했습니다.");
  }

  /**
   * 메시지를 포함한 생성자
   *
   * @param message 구체적인 에러 메시지
   */
  public BusinessException(String message) {
    super(message);
  }

  /**
   * 원인 예외를 포함한 생성자
   *
   * @param message 에러 메시지
   * @param cause 원인 예외
   */
  public BusinessException(String message, Throwable cause) {
    super(message, cause);
  }
}
