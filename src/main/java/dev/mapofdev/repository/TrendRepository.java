package dev.mapofdev.repository;

import java.util.List;
import java.util.Optional;

import dev.mapofdev.domain.Trend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 트렌드 데이터 접근
 * Trend 엔티티의 CRUD 및 조회 메서드를 제공하며,
 * 카테고리, 기간, 성장률 등 다양한 조건으로 검색과 정렬을 지원합니다.
 */
@Repository
public interface TrendRepository extends JpaRepository<Trend, Long> {

  // Category 기준으로 트렌드 목록 조회 (예: 백엔드, 프론트엔드)
  List<Trend> findByCategory(String category);

  // Period 기준으로 트렌드 목록 조회 (예: "2024-01")
  List<Trend> findByPeriod(String period);

  // 성장률(growthRate) 내림차순으로 전체 트렌드 조회
  List<Trend> findAllByOrderByGrowthRateDesc();

  // 생성일(createdAt) 기준 최신 트렌드 상위 5개 조회
  List<Trend> findTop5ByOrderByCreatedAtDesc();

  // 커스텀 JPQL: growthRate가 rate 초과하는 핫 카테고리 조회
  @Query("SELECT t FROM Trend t WHERE t.growthRate > :rate ORDER BY t.growthRate DESC")
  // 주어진 성장률보다 큰 트렌드들을 내림차순으로 직접 쿼리 조회 (입문자 기준: 동작만 이해하면 충분, 내부 원리 몰라도 됨)
  List<Trend> findHotCategories(@Param("rate") Double rate);

  // 카테고리와 기간으로 단일 트렌드 조회
  // 카테고리와 기간이 모두 일치하는 트렌드를 자동으로 조회 (JPA가 메서드 이름 분석해서 쿼리 생성)
  Optional<Trend> findByCategoryAndPeriod(String category, String period);
}
