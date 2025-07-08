package dev.mapofdev.service;

import dev.mapofdev.domain.Trend;
import dev.mapofdev.dto.TrendDto;
import dev.mapofdev.repository.TrendRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
   * 카테고리별 트렌드 조회
   */
  public List<TrendDto> getTrendsByCategory(String category) {
    log.debug("카테고리별 트렌드 조회: {}", category);

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
}
