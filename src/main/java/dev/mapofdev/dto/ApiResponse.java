package dev.mapofdev.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * API 응답 표준화 객체
 *
 * mapofdev 프로젝트에서 모든 REST API의 응답을
 * 일관된 형태로 클라이언트에게 전달하기 위해
 * 성공/실패 여부, 메시지, 데이터를 포함하는 표준 응답 형식을 제공하는 DTO
 */
@Getter // getter 메서드 자동 생성
@Builder
@AllArgsConstructor
public class ApiResponse<T> { // 제네릭으로 다양한 데이터 타입 지원

  private boolean success; // 성공/실패 여부
  private String message; // 응답 메시지
  private T data; // 실제 응답 데이터 (제네릭 타입)

  // 성공 응답 생성 (기본 메시지)
  public static <T> ApiResponse<T> success(T data) {
    return ApiResponse.<T>builder()
      .success(true)
      .message("요청이 성공적으로 처리되었습니다.")
      .data(data)
      .build();
  }

  // 성공 응답 생성 (커스텀 메시지)
  public static <T> ApiResponse<T> success(T data, String message) {
    return ApiResponse.<T>builder()
      .success(true)
      .message(message)
      .data(data)
      .build();
  }

  // 실패 응답 생성
  public static <T> ApiResponse<T> fail(String message) {
    return ApiResponse.<T>builder()
      .success(false)
      .message(message)
      .data(null) // 실패시에는 데이터 없음
      .build();
  }
}
