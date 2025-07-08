package dev.mapofdev.repository;

import dev.mapofdev.domain.JobCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * 직무 카테고리 데이터 접근
 *
 * mapofdev 프로젝트에서 개발자 직무 카테고리(JobCategory) 정보를
 * 데이터베이스로부터 조회하고 관리하기 위해 Spring Data JPA 기반으로
 * 채용공고 순 정렬, 신입 친화적 직군 검색, 연봉 순 정렬 등의 메서드를 제공하는 레포지토리
 */
@Repository // Spring이 이 인터페이스를 데이터 접근 계층으로 인식
public interface JobCategoryRepository extends JpaRepository<JobCategory, Long> { // JPA 기본 기능 상속

  // 채용공고 많은 순서로 직무 카테고리 조회 (채용 기회 많은 직군부터)
  List<JobCategory> findAllByOrderByTotalJobsDesc();

  // 신입 채용이 있는 직군만 조회 (신입 채용공고 많은 순)
  @Query("SELECT j FROM JobCategory j WHERE j.juniorJobs > 0 ORDER BY j.juniorJobs DESC")
  List<JobCategory> findJuniorFriendlyCategories();

  // 평균 연봉 높은 순서로 직무 카테고리 조회 (고연봉 직군부터)
  List<JobCategory> findAllByOrderByAvgSalaryDesc();
}
