package dev.mapofdev.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 트렌드 데이터 전송 객체
 *
 * mapofdev 프로젝트에서 기술 트렌드(Trend) 정보를
 * 클라이언트와 서버 간에 전송하기 위해 JSON 형태로
 * 직렬화/역직렬화, 성장률 아이콘 표시, 핫 카테고리 판별 등의 기능을
 * 제공하는 DTO
 */
@Getter // getter 메서드 자동 생성
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrendDto {

  private Long id;
  private String category;
  private Integer jobCount;
  private Double growthRate;
  private String description;
  private String period;
  private Boolean hot;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // JSON 변환시 날짜 형식 지정
  private LocalDateTime createdAt;

  private boolean isHot;
  private String growthIcon;

  // Entity를 DTO로 변환
  public static TrendDto from(dev.mapofdev.domain.Trend trend) {
    return TrendDto.builder()
      .id(trend.getId())
      .category(trend.getCategory())
      .jobCount(trend.getJobCount())
      .growthRate(trend.getGrowthRate())
      .description(trend.getDescription())
      .period(trend.getPeriod())
      .createdAt(trend.getCreatedAt())
      .isHot(trend.isHotCategory())
      .growthIcon(trend.getGrowthRate() > 0 ? "↑" :
        trend.getGrowthRate() < 0 ? "↓" : "→")
      .build();
  }
}
