package dev.mapofdev.service;

import dev.mapofdev.domain.Skill;
import dev.mapofdev.dto.SkillDto;
import dev.mapofdev.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 기술스택 비즈니스 로직 서비스
 *
 * mapofdev 프로젝트에서 개발자 기술스택(Skill) 관련 비즈니스 로직을
 * 처리하기 위해 인기 기술 조회, 카테고리별 검색, 기술 랭킹 등의 서비스 메서드를
 * 제공하고 캐싱 최적화와 로깅을 담당하는 서비스 클래스
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SkillService {

  private final SkillRepository skillRepository;

  /**
   * 인기 기술 스택 TOP 10
   */
  @Cacheable(value = "popularSkills", cacheManager = "cacheManager") // 결과를 캐시에 저장
  public List<SkillDto> getPopularSkills() {
    log.debug("인기 기술 스택 조회");

    List<Skill> skills = skillRepository.findTop10ByOrderByDemandCountDesc();

    return skills.stream()
      .map(SkillDto::from) // Entity를 DTO로 변환
      .collect(Collectors.toList());
  }

  /**
   * 카테고리별 기술 조회
   */
  public List<SkillDto> getSkillsByCategory(String category) {
    log.debug("카테고리별 기술 조회: {}", category);

    List<Skill> skills = skillRepository.findByCategory(category);

    return skills.stream()
      .map(SkillDto::from)
      .collect(Collectors.toList());
  }

  /**
   * 기술 검색
   */
  public List<SkillDto> searchSkills(String keyword) {
    log.debug("기술 검색: {}", keyword);

    if (keyword == null || keyword.trim().isEmpty()) { // 빈 검색어 체크
      return List.of(); // 빈 리스트 반환
    }

    List<Skill> skills = skillRepository.findByNameContainingIgnoreCase(keyword);

    return skills.stream()
      .map(SkillDto::from)
      .collect(Collectors.toList());
  }

  /**
   * 전체 기술 랭킹
   */
  public List<SkillDto> getAllSkillsRanking() {
    log.debug("전체 기술 랭킹 조회");

    List<Skill> skills = skillRepository.findAllByOrderByRankingAsc(); // 랭킹 오름차순

    return skills.stream()
      .map(SkillDto::from)
      .collect(Collectors.toList());
  }
}
