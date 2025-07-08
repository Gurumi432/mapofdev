package dev.mapofdev.exception;

/**
 * 리소스를 찾을 수 없을 때 발생하는 예외
 *
 * 데이터베이스에서 요청된 엔티티가 존재하지 않거나
 * 잘못된 ID로 조회할 때 사용하는 커스텀 예외 클래스입니다.
 */
@SuppressWarnings("unused") // 사용되지 않는 생성자에 대한 경고 억제
public class ResourceNotFoundException extends RuntimeException {

  /**
   * 기본 생성자
   */
  public ResourceNotFoundException() {
    super("요청하신 리소스를 찾을 수 없습니다.");
  }

  /**
   * 메시지를 포함한 생성자
   *
   * @param message 구체적인 에러 메시지
   */
  public ResourceNotFoundException(String message) {
    super(message);
  }

  /**
   * 리소스 타입과 ID를 포함한 생성자
   *
   * @param resourceName 리소스 타입 (예: "Trend", "Skill")
   * @param fieldName 필드명 (예: "id", "category")
   * @param fieldValue 필드값 (예: "123", "백엔드")
   */
  public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
    super(String.format("%s를 찾을 수 없습니다. %s: %s", resourceName, fieldName, fieldValue));
  }

  /**
   * 원인 예외를 포함한 생성자
   *
   * @param message 에러 메시지
   * @param cause 원인 예외
   */
  public ResourceNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
