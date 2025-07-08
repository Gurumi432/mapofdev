package dev.mapofdev.domain;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

/**
 직무 카테고리 관리의 기능
 이 클래스는 Spring Boot 애플리케이션의 'job_categories' 테이블에서
 직무 카테고리 식별(id), 이름(name), 전체 채용공고 수(totalJobs),
 신입 채용공고 수(juniorJobs), 평균 연봉(avgSalary), 필수 기술(requiredSkills)을
 정의하여 저장·조회·수정·삭제를 지원합니다.
 */
@Entity
@Table(name = "job_categories") // DB에 'job_categories' 테이블 생성 및 매핑
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobCategory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 자동 증가 설정
  private Long id;  // 카테고리 고유 식별자

  @Column(nullable = false, unique = true) // NOT NULL, 유니크
  private String name;  // 직무 이름 (백엔드 개발자, 프론트엔드 개발자 등)

  @Column(nullable = false) // NOT NULL
  private Integer totalJobs;  // 전체 채용공고 수

  @Column(nullable = false) // NOT NULL
  private Integer juniorJobs;  // 신입 채용공고 수

  @Column(nullable = false) // NOT NULL
  private BigDecimal avgSalary;  // 평균 연봉

  @Column(length = 1000) // VARCHAR(1000)
  private String requiredSkills;  // 필수 기술 (콤마 구분)
}
