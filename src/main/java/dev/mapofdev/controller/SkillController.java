package dev.mapofdev.controller;

import dev.mapofdev.dto.ApiResponse;
import dev.mapofdev.dto.SkillDto;
import dev.mapofdev.service.SkillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 기술스택 REST API 컨트롤러
 *
 * mapofdev 프로젝트에서 개발자 기술스택(Skill) 관련 HTTP 요청을
 * 처리하기 위해 클라이언트의 요청을 받아서 서비스 계층에 전달하고
 * 인기 기술 조회, 검색, 랭킹 등의 응답을 표준화된 형태로 반환하는 REST API 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/skills")
@RequiredArgsConstructor
@Tag(name = "Skill API", description = "기술 스택 관련 API")
public class SkillController {

  private final SkillService skillService;

  /**
   * 인기 기술 스택 조회
   */
  @GetMapping("/popular")
  @Operation(summary = "인기 기술 스택 TOP 10", description = "가장 수요가 많은 기술 스택 10개를 조회합니다.")
  public ResponseEntity<ApiResponse<List<SkillDto>>> getPopularSkills() {
    log.info("GET /api/v1/skills/popular - 인기 기술 조회");

    try {
      List<SkillDto> skills = skillService.getPopularSkills();
      return ResponseEntity.ok(ApiResponse.success(skills));
    } catch (Exception e) {
      log.error("인기 기술 조회 실패", e);
      return ResponseEntity.internalServerError()
        .body(ApiResponse.fail("기술 조회에 실패했습니다."));
    }
  }

  /**
   * 카테고리별 기술 조회
   */
  @GetMapping("/category/{category}")
  @Operation(summary = "카테고리별 기술 조회", description = "특정 카테고리의 기술들을 조회합니다.")
  public ResponseEntity<ApiResponse<List<SkillDto>>> getSkillsByCategory(
    @Parameter(description = "카테고리", example = "Language")
    @PathVariable String category) {
    log.info("GET /api/v1/skills/category/{} - 카테고리별 기술 조회", category);

    try {
      List<SkillDto> skills = skillService.getSkillsByCategory(category);
      return ResponseEntity.ok(ApiResponse.success(skills));
    } catch (Exception e) {
      log.error("카테고리별 기술 조회 실패: {}", category, e);
      return ResponseEntity.internalServerError()
        .body(ApiResponse.fail("기술 조회에 실패했습니다."));
    }
  }

  /**
   * 기술 검색
   */
  @GetMapping("/search")
  @Operation(summary = "기술 검색", description = "키워드로 기술을 검색합니다.")
  public ResponseEntity<ApiResponse<List<SkillDto>>> searchSkills(
    @Parameter(description = "검색 키워드", example = "Java")
    @RequestParam(required = false) String keyword) { // 쿼리 파라미터로 검색어 받기
    log.info("GET /api/v1/skills/search?keyword={} - 기술 검색", keyword);

    try {
      List<SkillDto> skills = skillService.searchSkills(keyword);
      return ResponseEntity.ok(ApiResponse.success(skills));
    } catch (Exception e) {
      log.error("기술 검색 실패: {}", keyword, e);
      return ResponseEntity.internalServerError()
        .body(ApiResponse.fail("기술 검색에 실패했습니다."));
    }
  }

  /**
   * 전체 기술 랭킹
   */
  @GetMapping("/ranking")
  @Operation(summary = "전체 기술 랭킹", description = "모든 기술의 랭킹을 조회합니다.")
  public ResponseEntity<ApiResponse<List<SkillDto>>> getAllSkillsRanking() {
    log.info("GET /api/v1/skills/ranking - 전체 기술 랭킹 조회");

    try {
      List<SkillDto> skills = skillService.getAllSkillsRanking();
      return ResponseEntity.ok(ApiResponse.success(skills));
    } catch (Exception e) {
      log.error("기술 랭킹 조회 실패", e);
      return ResponseEntity.internalServerError()
        .body(ApiResponse.fail("기술 랭킹 조회에 실패했습니다."));
    }
  }
}
