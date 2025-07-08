package dev.mapofdev.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * 직무 카테고리 데이터 전송 객체
 *
 * 개발자 직무 카테고리(JobCategory) 정보를
 * 클라이언트와 서버 간에 전송하기 위해 JSON 형태로
 * 채용공고 수, 신입 비율, 연봉 수준 계산 등의 기능을 제공하는 DTO
 */
@Getter // getter 메서드 자동 생성
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobCategoryDto {

  private Long id;
  private String name;
  private Integer totalJobs;
  private Integer juniorJobs;
  private BigDecimal avgSalary;
  private List<String> requiredSkills;

  // 화면 표시용 계산 필드들
  private Double juniorRatio; // 신입 채용 비율 (%)
  private String salaryLevel; // 연봉 수준 표시용

  // Entity를 DTO로 변환
  public static JobCategoryDto from(dev.mapofdev.domain.JobCategory category) {
    // 필수 기술을 콤마로 분리하여 리스트로 변환
    List<String> skills = category.getRequiredSkills() != null ?
      Arrays.asList(category.getRequiredSkills().split(",")) : List.of();

    // 신입 채용 비율 계산 (%)
    double ratio = category.getTotalJobs() > 0 ?
      (double) category.getJuniorJobs() / category.getTotalJobs() * 100 : 0;

    // 연봉 수준 분류 (5000만원 기준 높음, 4000만원 기준 중간)
    String level = category.getAvgSalary().compareTo(new BigDecimal("5000")) > 0 ? "높음" :
      category.getAvgSalary().compareTo(new BigDecimal("4000")) > 0 ? "중간" : "낮음";

    return JobCategoryDto.builder()
      .id(category.getId())
      .name(category.getName())
      .totalJobs(category.getTotalJobs())
      .juniorJobs(category.getJuniorJobs())
      .avgSalary(category.getAvgSalary())
      .requiredSkills(skills)
      .juniorRatio(Math.round(ratio * 10) / 10.0) // 소수점 첫째 자리까지
      .salaryLevel(level)
      .build();
  }
}
