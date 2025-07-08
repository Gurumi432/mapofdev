package dev.mapofdev.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

/**
 채용 트렌드 관리의 기능
 이 클래스는 Spring Boot 애플리케이션의 'trends' 테이블에서
 백엔드, 프론트엔드, 데이터 등 카테고리별 채용공고 수(jobCount)와
 성장률(growthRate)을 기간(period)별로 관리하며,
 생성(createdAt)과 수정(updatedAt) 시각을 자동 기록하여
 성장 여부(isGrowing)와 인기 카테고리(isHotCategory)를
 판단하는 역할을 수행합니다.
 */
@Entity
@Table(name = "trends", indexes = {
  @Index(name = "idx_category", columnList = "category"),  // 조회 성능을 위해 category에 인덱스 추가
  @Index(name = "idx_created_at", columnList = "createdAt") // 생성일 기준 정렬용 인덱스
})
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@ToString(exclude = {"createdAt", "updatedAt"})
public class Trend {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 자동 증가 설정
  @Column(name = "trend_id")
  private Long id; // 트렌드 고유 식별자

  @Column(nullable = false, length = 100)
  private String category; // 분야 (예: 백엔드, 프론트엔드 등)

  @Column(nullable = false)
  private Integer jobCount; // 채용 공고 수

  @Column(nullable = false, precision = 5)
  private Double growthRate; // 성장률 (퍼센트 단위)

  @Column(length = 500)
  private String description; // 트렌드 설명 (선택 입력)

  @Column(nullable = false)
  private String period; // 통계 기간 (예: "2024-01")

  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createdAt; // 생성 시간 자동 기록

  @UpdateTimestamp
  private LocalDateTime updatedAt; // 수정 시간 자동 기록

  // 성장 중인지 확인 (성장률 > 0)
  public boolean isGrowing() {
    return this.growthRate > 0;
  }

  // 성장률이 10% 이상이면 인기 카테고리로 판단
  public boolean isHotCategory() {
    return this.growthRate > 10.0;
  }
}
