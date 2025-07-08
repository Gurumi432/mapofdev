package dev.mapofdev.domain;

import jakarta.persistence.*;
import lombok.*;

/**
 스킬 관리의 기능
 이 클래스는 Spring Boot 애플리케이션의 'skills' 테이블에서
 스킬 식별(id), 이름(name), 카테고리(category), 수요(demandCount), 순위(ranking),
 아이콘 URL(iconUrl)을 정의하여 저장·조회·수정·삭제 기능을 지원합니다.
 */
@Entity
@Table(name = "skills") // DB에 'skills' 테이블 생성 및 매핑
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Skill {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 자동 증가 설정
  private Long id;  // 스킬 고유 식별자

  @Column(nullable = false, unique = true, length = 50) // NOT NULL, 유니크, VARCHAR(50)
  private String name;  // 스킬 이름 (Java, Spring, React 등)

  @Column(nullable = false) // NOT NULL
  private String category;  // 분류 (Language, Framework, Database 등)

  @Column(nullable = false) // NOT NULL
  private Integer demandCount;  // 수요 수

  @Column(nullable = false) // NOT NULL
  private Integer ranking;  // 순위

  private String iconUrl;  // 아이콘 URL
}
