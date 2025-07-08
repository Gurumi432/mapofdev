package dev.mapofdev.repository;

import dev.mapofdev.domain.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * 기술스택 데이터 접근
 *
 * mapofdev 프로젝트에서 개발자들이 사용하는 기술스택(Skill) 정보를,
 * 데이터베이스로부터 조회하고 관리하기 위해 Spring Data JPA 기반으로,
 * 검색 및 정렬 등의 메서드를 제공하는 레포지토리
 */
@Repository // Spring이 이 인터페이스를 데이터 접근 계층으로 인식
public interface SkillRepository extends JpaRepository<Skill, Long> { // JPA 기본 기능 상속

  // 카테고리로 기술스택 찾기 (예: "Backend" 입력시 백엔드 기술들 반환)
  List<Skill> findByCategory(String category);

  // 랭킹 순서대로 모든 기술스택 조회 (1위, 2위, 3위... 순)
  List<Skill> findAllByOrderByRankingAsc();

  // 인기 높은 상위 10개 기술스택 조회 (수요량 많은 순)
  List<Skill> findTop10ByOrderByDemandCountDesc();

  // 기술 이름에 키워드 포함된 것들 검색 (대소문자 무관)
  List<Skill> findByNameContainingIgnoreCase(String keyword);
}
