package dev.mapofdev.dto;

import lombok.*;

/**
 * 기술스택 데이터 전송 객체
 *
 * mapofdev 프로젝트에서 개발자 기술스택(Skill) 정보를
 * 클라이언트와 서버 간에 전송하기 위해 JSON 형태로
 * 수요량, 랭킹, 트렌드 표시 등의 기능을 제공하는 DTO
 */
@Getter // getter 메서드 자동 생성
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkillDto {

  private Long id;
  private String name;
  private String category;
  private Integer demandCount;
  private Integer ranking;
  private String iconUrl;

  private String trend; // "상승", "하락", "유지"

  // Entity를 DTO로 변환
  public static SkillDto from(dev.mapofdev.domain.Skill skill) {
    return SkillDto.builder()
      .id(skill.getId())
      .name(skill.getName())
      .category(skill.getCategory())
      .demandCount(skill.getDemandCount())
      .ranking(skill.getRanking())
      .iconUrl(skill.getIconUrl())
      .trend("유지") // TODO: 이전 데이터와 비교해서 변경 예정
      .build();
  }
}
