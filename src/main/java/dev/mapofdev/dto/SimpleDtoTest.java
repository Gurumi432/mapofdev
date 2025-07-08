// src/test/java/dev/mapofdev/dto/SimpleDtoTest.java
package dev.mapofdev.dto;

import dev.mapofdev.domain.Trend;

public class SimpleDtoTest {

  public static void main(String[] args) {
    try {
      // Trend 테스트
      Trend trend = new Trend();
      trend.setId(1L);
      trend.setCategory("Backend");
      trend.setJobCount(100);
      trend.setGrowthRate(15.5);

      // DTO 변환 테스트
      TrendDto dto = TrendDto.from(trend);

      // 결과 확인
      System.out.println("✅ ID: " + dto.getId());
      System.out.println("✅ Category: " + dto.getCategory());
      System.out.println("✅ JobCount: " + dto.getJobCount());
      System.out.println("✅ GrowthRate: " + dto.getGrowthRate());
      System.out.println("🎉 TrendDto.from() 메서드 정상 작동!");

    } catch (Exception e) {
      System.out.println("❌ 오류 발생: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
