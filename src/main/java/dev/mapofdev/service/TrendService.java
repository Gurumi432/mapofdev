package dev.mapofdev.service;

import dev.mapofdev.domain.Trend;
import dev.mapofdev.dto.TrendDto;
import dev.mapofdev.exception.BusinessException;
import dev.mapofdev.exception.ResourceNotFoundException;
import dev.mapofdev.repository.TrendRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 트렌드 비즈니스 로직 서비스
 *
 * 기술 트렌드(Trend) 관련 비즈니스 로직을
 * 처리하기 위해 데이터 조회, 변환, 생성 등의 서비스 메서드를
 * 제공하고 트랜잭션 관리와 로깅을 담당하는 서비스 클래스
 */
@Slf4j // 로깅 기능 자동 생성
@Service // Spring의 서비스 컴포넌트로 등록
@RequiredArgsConstructor // final 필드에 대한 생성자 자동 생성
@Transactional(readOnly = true) // 기본적으로 읽기 전용 트랜잭션
public class TrendService {

  private final TrendRepository trendRepository; // 데이터 접근을 위한 Repository

  // 유효한 카테고리 목록 정의
  private static final List<String> VALID_CATEGORIES = Arrays.asList(
    "백엔드 개발", "프론트엔드 개발", "데이터 엔지니어",
    "DevOps/SRE", "AI/ML 엔지니어"
  );

  /**
   * 모든 트렌드 조회
   */
  public List<TrendDto> getAllTrends() {
    log.debug("모든 트렌드 조회 시작");

    List<Trend> trends = trendRepository.findAll(); // DB에서 모든 트렌드 조회

    log.info("조회된 트렌드 수: {}", trends.size());

    return trends.stream() // 스트림으로 변환
      .map(TrendDto::from) // Entity를 DTO로 변환
      .collect(Collectors.toList()); // 리스트로 수집
  }

  /**
   * ID로 트렌드 조회 (ResourceNotFoundException 사용)
   */
  public TrendDto getTrendById(Long id) {
    log.debug("ID로 트렌드 조회: {}", id);

    // ID 유효성 검사
    if (id == null || id <= 0) {
      throw new BusinessException("유효하지 않은 트렌드 ID입니다: " + id);
    }

    Trend trend = trendRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Trend", "id", id));

    return TrendDto.from(trend);
  }

  /**
   * 카테고리별 트렌드 조회 (BusinessException 사용)
   */
  public List<TrendDto> getTrendsByCategory(String category) {
    log.debug("카테고리별 트렌드 조회: {}", category);

    // 카테고리 유효성 검사
    validateCategory(category);

    List<Trend> trends = trendRepository.findByCategory(category);

    return trends.stream()
      .map(TrendDto::from)
      .collect(Collectors.toList());
  }

  /**
   * 핫한 카테고리 조회 (성장률 10% 이상)
   */
  public List<TrendDto> getHotTrends() {
    log.debug("핫한 카테고리 조회");

    List<Trend> hotTrends = trendRepository.findHotCategories(10.0); // 성장률 10% 이상

    return hotTrends.stream()
      .map(TrendDto::from)
      .collect(Collectors.toList());
  }

  /**
   * 최신 트렌드 TOP 5
   */
  public List<TrendDto> getLatestTrends() {
    log.debug("최신 트렌드 조회");

    List<Trend> latestTrends = trendRepository.findTop5ByOrderByCreatedAtDesc(); // 최신 5개

    return latestTrends.stream()
      .map(TrendDto::from)
      .collect(Collectors.toList());
  }

  /**
   * 트렌드 생성 (테스트용)
   */
  @Transactional // 쓰기 작업이므로 읽기 전용 해제
  public TrendDto createTrend(TrendDto dto) {
    log.debug("트렌드 생성: {}", dto.getCategory());

    // 입력값 유효성 검사
    validateTrendDto(dto);

    // 카테고리 유효성 검사
    validateCategory(dto.getCategory());

    // 중복 체크 (같은 카테고리와 기간)
    if (trendRepository.findByCategoryAndPeriod(dto.getCategory(), dto.getPeriod()).isPresent()) {
      throw new BusinessException("이미 해당 기간에 동일한 카테고리의 트렌드가 존재합니다.");
    }

    Trend trend = Trend.builder() // 빌더 패턴으로 객체 생성
      .category(dto.getCategory())
      .jobCount(dto.getJobCount())
      .growthRate(dto.getGrowthRate())
      .description(dto.getDescription())
      .period(dto.getPeriod())
      .build();

    Trend savedTrend = trendRepository.save(trend); // DB에 저장

    log.info("트렌드 생성 완료: ID={}", savedTrend.getId());

    return TrendDto.from(savedTrend); // 저장된 Entity를 DTO로 변환하여 반환
  }

  /**
   * 카테고리 유효성 검증
   *
   * @param category 검증할 카테고리
   * @throws BusinessException 유효하지 않은 카테고리인 경우
   */
  private void validateCategory(String category) {
    if (category == null || category.trim().isEmpty()) {
      throw new BusinessException("카테고리는 필수 입력값입니다.");
    }

    if (!VALID_CATEGORIES.contains(category)) {
      throw new BusinessException("지원하지 않는 카테고리입니다: " + category +
        ". 지원 카테고리: " + String.join(", ", VALID_CATEGORIES));
    }
  }

  /**
   * 트렌드 DTO 유효성 검증
   *
   * @param dto 검증할 트렌드 DTO
   * @throws BusinessException 유효하지 않은 데이터인 경우
   */
  private void validateTrendDto(TrendDto dto) {
    if (dto == null) {
      throw new BusinessException("트렌드 데이터가 누락되었습니다.");
    }

    if (dto.getJobCount() == null || dto.getJobCount() < 0) {
      throw new BusinessException("채용 공고 수는 0 이상이어야 합니다.");
    }

    if (dto.getGrowthRate() == null) {
      throw new BusinessException("성장률은 필수 입력값입니다.");
    }

    if (dto.getPeriod() == null || dto.getPeriod().trim().isEmpty()) {
      throw new BusinessException("통계 기간은 필수 입력값입니다.");
    }

    // 기간 형식 검증 (YYYY-MM)
    if (!dto.getPeriod().matches("\\d{4}-\\d{2}")) {
      throw new BusinessException("기간 형식이 올바르지 않습니다. YYYY-MM 형식으로 입력해주세요.");
    }
  }
}
