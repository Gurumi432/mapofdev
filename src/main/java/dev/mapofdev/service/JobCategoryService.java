package dev.mapofdev.service;

import dev.mapofdev.domain.JobCategory;
import dev.mapofdev.dto.JobCategoryDto;
import dev.mapofdev.repository.JobCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 직무 카테고리 비즈니스 로직 서비스
 *
 * mapofdev 프로젝트에서 개발자 직무 카테고리(JobCategory) 관련 비즈니스 로직을
 * 처리하기 위해 전체 직군 조회, 채용공고 순 정렬, 신입 친화적 직군 검색 등의 서비스 메서드를
 * 제공하고 트랜잭션 관리와 로깅을 담당하는 서비스 클래스
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobCategoryService {

  private final JobCategoryRepository jobCategoryRepository;

  /**
   * 전체 직군 조회
   */
  public List<JobCategoryDto> getAllCategories() {
    log.debug("전체 직군 조회");

    List<JobCategory> categories = jobCategoryRepository.findAll();

    return categories.stream()
      .map(JobCategoryDto::from) // Entity를 DTO로 변환
      .collect(Collectors.toList());
  }

  /**
   * 채용 많은 순 직군
   */
  public List<JobCategoryDto> getCategoriesByJobCount() {
    log.debug("채용 많은 순 직군 조회");

    List<JobCategory> categories = jobCategoryRepository.findAllByOrderByTotalJobsDesc();

    return categories.stream()
      .map(JobCategoryDto::from)
      .collect(Collectors.toList());
  }

  /**
   * 신입 친화적 직군
   */
  public List<JobCategoryDto> getJuniorFriendlyCategories() {
    log.debug("신입 친화적 직군 조회");

    List<JobCategory> categories = jobCategoryRepository.findJuniorFriendlyCategories();

    return categories.stream()
      .map(JobCategoryDto::from)
      .collect(Collectors.toList());
  }

  /**
   * 고연봉 직군 TOP 5
   */
  public List<JobCategoryDto> getHighSalaryCategories() {
    log.debug("고연봉 직군 조회");

    List<JobCategory> categories = jobCategoryRepository.findAllByOrderByAvgSalaryDesc();

    return categories.stream()
      .limit(5) // 상위 5개만 선택
      .map(JobCategoryDto::from)
      .collect(Collectors.toList());
  }
}
