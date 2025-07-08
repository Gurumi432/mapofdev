package dev.mapofdev.controller;

import dev.mapofdev.dto.ApiResponse;
import dev.mapofdev.dto.TrendDto;
import dev.mapofdev.service.TrendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 트렌드 REST API 컨트롤러
 *
 * mapofdev 프로젝트에서 기술 트렌드(Trend) 관련 HTTP 요청을
 * 처리하기 위해 클라이언트의 요청을 받아서 서비스 계층에 전달하고
 * 응답을 표준화된 형태로 반환하는 REST API 컨트롤러
 */
@Slf4j
@RestController // REST API 컨트롤러로 등록
@RequestMapping("/api/v1/trends") // 기본 URL 경로 설정
@RequiredArgsConstructor
@Tag(name = "Trend API", description = "채용 트렌드 관련 API") // Swagger 문서용

public class TrendController {

  private final TrendService trendService; // 비즈니스 로직 처리를 위한 서비스

  /**
   * 전체 트렌드 조회
   */
  @GetMapping // GET /api/v1/trends
  @Operation(summary = "전체 트렌드 조회", description = "모든 채용 트렌드를 조회합니다.")
  public ResponseEntity<ApiResponse<List<TrendDto>>> getAllTrends() {
    log.info("GET /api/v1/trends - 전체 트렌드 조회 요청");

    try {
      List<TrendDto> trends = trendService.getAllTrends();
      return ResponseEntity.ok(ApiResponse.success(trends)); // 200 OK 응답
    } catch (Exception e) {
      log.error("트렌드 조회 실패", e);
      return ResponseEntity.internalServerError() // 500 에러 응답
        .body(ApiResponse.fail("트렌드 조회에 실패했습니다."));
    }
  }

  /**
   * 카테고리별 트렌드 조회
   */
  @GetMapping("/category/{category}") // URL 경로에서 카테고리 값 받기
  @Operation(summary = "카테고리별 트렌드 조회", description = "특정 카테고리의 트렌드를 조회합니다.")
  public ResponseEntity<ApiResponse<List<TrendDto>>> getTrendsByCategory(
    @Parameter(description = "카테고리명", example = "백엔드")
    @PathVariable String category) { // URL 경로의 값을 파라미터로 받기
    log.info("GET /api/v1/trends/category/{} - 카테고리별 트렌드 조회", category);

    try {
      List<TrendDto> trends = trendService.getTrendsByCategory(category);
      return ResponseEntity.ok(ApiResponse.success(trends));
    } catch (Exception e) {
      log.error("카테고리별 트렌드 조회 실패: {}", category, e);
      return ResponseEntity.internalServerError()
        .body(ApiResponse.fail("트렌드 조회에 실패했습니다."));
    }
  }

  /**
   * 핫한 트렌드 조회
   */
  @GetMapping("/hot")
  @Operation(summary = "핫한 트렌드 조회", description = "성장률이 높은 핫한 트렌드를 조회합니다.")
  public ResponseEntity<ApiResponse<List<TrendDto>>> getHotTrends() {
    log.info("GET /api/v1/trends/hot - 핫한 트렌드 조회");

    try {
      List<TrendDto> trends = trendService.getHotTrends();
      return ResponseEntity.ok(ApiResponse.success(trends));
    } catch (Exception e) {
      log.error("핫한 트렌드 조회 실패", e);
      return ResponseEntity.internalServerError()
        .body(ApiResponse.fail("트렌드 조회에 실패했습니다."));
    }
  }

  /**
   * 최신 트렌드 조회
   */
  @GetMapping("/latest")
  @Operation(summary = "최신 트렌드 조회", description = "최근 등록된 트렌드 TOP 5를 조회합니다.")
  public ResponseEntity<ApiResponse<List<TrendDto>>> getLatestTrends() {
    log.info("GET /api/v1/trends/latest - 최신 트렌드 조회");

    try {
      List<TrendDto> trends = trendService.getLatestTrends();
      return ResponseEntity.ok(ApiResponse.success(trends));
    } catch (Exception e) {
      log.error("최신 트렌드 조회 실패", e);
      return ResponseEntity.internalServerError()
        .body(ApiResponse.fail("트렌드 조회에 실패했습니다."));
    }
  }

  /**
   * 트렌드 생성 (테스트용)
   */
  @PostMapping // POST /api/v1/trends
  @Operation(summary = "트렌드 생성", description = "새로운 트렌드를 생성합니다. (테스트용)")
  public ResponseEntity<ApiResponse<TrendDto>> createTrend(
    @RequestBody TrendDto trendDto) { // 요청 본문의 JSON을 DTO로 변환
    log.info("POST /api/v1/trends - 트렌드 생성: {}", trendDto.getCategory());

    try {
      TrendDto created = trendService.createTrend(trendDto);
      return ResponseEntity.ok(ApiResponse.success(created, "트렌드가 생성되었습니다."));
    } catch (Exception e) {
      log.error("트렌드 생성 실패", e);
      return ResponseEntity.internalServerError()
        .body(ApiResponse.fail("트렌드 생성에 실패했습니다."));
    }
  }
  /**
   * ID로 트렌드 조회 (예외 처리 테스트용)
   */
  @GetMapping("/{id}")
  @Operation(summary = "ID로 트렌드 조회", description = "특정 ID의 트렌드를 조회합니다.")
  public ResponseEntity<ApiResponse<TrendDto>> getTrendById(
    @Parameter(description = "트렌드 ID", example = "1")
    @PathVariable Long id) {
    log.info("GET /api/v1/trends/{} - ID로 트렌드 조회", id);

    // try-catch 제거 - GlobalExceptionHandler가 자동으로 처리
    TrendDto trend = trendService.getTrendById(id);
    return ResponseEntity.ok(ApiResponse.success(trend));
  }
}
