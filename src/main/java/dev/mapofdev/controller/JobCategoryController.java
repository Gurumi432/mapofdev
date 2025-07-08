package dev.mapofdev.controller;

import dev.mapofdev.dto.ApiResponse;
import dev.mapofdev.dto.JobCategoryDto;
import dev.mapofdev.service.JobCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 직무 카테고리 REST API 컨트롤러
 *
 * mapofdev 프로젝트에서 개발자 직무 카테고리(JobCategory) 관련 HTTP 요청을
 * 처리하기 위해 클라이언트의 요청을 받아서 서비스 계층에 전달하고
 * 직군별 정보, 채용 현황, 연봉 정보 등의 응답을 표준화된 형태로 반환하는 REST API 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/jobs/categories")
@RequiredArgsConstructor
@Tag(name = "Job Category API", description = "직군 정보 관련 API")
public class JobCategoryController {

  private final JobCategoryService jobCategoryService;

  /**
   * 전체 직군 조회
   */
  @GetMapping
  @Operation(summary = "전체 직군 조회", description = "모든 개발 직군 정보를 조회합니다.")
  public ResponseEntity<ApiResponse<List<JobCategoryDto>>> getAllCategories() {
    log.info("GET /api/v1/jobs/categories - 전체 직군 조회");

    try {
      List<JobCategoryDto> categories = jobCategoryService.getAllCategories();
      return ResponseEntity.ok(ApiResponse.success(categories));
    } catch (Exception e) {
      log.error("직군 조회 실패", e);
      return ResponseEntity.internalServerError()
        .body(ApiResponse.fail("직군 조회에 실패했습니다."));
    }
  }

  /**
   * 채용 많은 순 직군
   */
  @GetMapping("/by-job-count")
  @Operation(summary = "채용 많은 순 직군", description = "채용공고가 많은 순으로 직군을 조회합니다.")
  public ResponseEntity<ApiResponse<List<JobCategoryDto>>> getCategoriesByJobCount() {
    log.info("GET /api/v1/jobs/categories/by-job-count - 채용 많은 순 직군 조회");

    try {
      List<JobCategoryDto> categories = jobCategoryService.getCategoriesByJobCount();
      return ResponseEntity.ok(ApiResponse.success(categories));
    } catch (Exception e) {
      log.error("채용 많은 순 직군 조회 실패", e);
      return ResponseEntity.internalServerError()
        .body(ApiResponse.fail("직군 조회에 실패했습니다."));
    }
  }

  /**
   * 신입 친화적 직군
   */
  @GetMapping("/junior-friendly")
  @Operation(summary = "신입 친화적 직군", description = "신입 채용이 많은 직군을 조회합니다.")
  public ResponseEntity<ApiResponse<List<JobCategoryDto>>> getJuniorFriendlyCategories() {
    log.info("GET /api/v1/jobs/categories/junior-friendly - 신입 친화적 직군 조회");

    try {
      List<JobCategoryDto> categories = jobCategoryService.getJuniorFriendlyCategories();
      return ResponseEntity.ok(ApiResponse.success(categories));
    } catch (Exception e) {
      log.error("신입 친화적 직군 조회 실패", e);
      return ResponseEntity.internalServerError()
        .body(ApiResponse.fail("직군 조회에 실패했습니다."));
    }
  }

  /**
   * 고연봉 직군
   */
  @GetMapping("/high-salary")
  @Operation(summary = "고연봉 직군 TOP 5", description = "평균 연봉이 높은 상위 5개 직군을 조회합니다.")
  public ResponseEntity<ApiResponse<List<JobCategoryDto>>> getHighSalaryCategories() {
    log.info("GET /api/v1/jobs/categories/high-salary - 고연봉 직군 조회");

    try {
      List<JobCategoryDto> categories = jobCategoryService.getHighSalaryCategories();
      return ResponseEntity.ok(ApiResponse.success(categories));
    } catch (Exception e) {
      log.error("고연봉 직군 조회 실패", e);
      return ResponseEntity.internalServerError()
        .body(ApiResponse.fail("직군 조회에 실패했습니다."));
    }
  }
}
