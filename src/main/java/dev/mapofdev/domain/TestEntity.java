package dev.mapofdev.domain;

import jakarta.persistence.*;

@Entity  // 🔥 이 클래스가 데이터베이스 테이블이라고 알려줌
@Table(name = "test_table")  // 테이블 이름을 "test_table"로 지정 (몰라도 됨)
public class TestEntity {

  @Id  // 🔥 이 필드가 기본키(Primary Key) - 각 행을 구분하는 고유 번호
  @GeneratedValue(strategy = GenerationType.IDENTITY)  // 🔥 자동으로 1, 2, 3... 증가
  private Long id;  // 숫자 타입 (1, 2, 3...)

  private String name;  // 🔥 문자열 타입 - 데이터베이스의 name 컬럼이 됨

  // 기본 생성자 (JPA가 내부적으로 사용, 몰라도 됨)
  public TestEntity() {}

  // 🔥 Getter - 데이터 꺼내기용
  public Long getId() {
    return id;  // id 값을 돌려줌
  }

  // 🔥 Setter - 데이터 넣기용
  public void setId(Long id) {
    this.id = id;  // id 값을 설정
  }

  // 🔥 Getter - name 데이터 꺼내기
  public String getName() {
    return name;  // name 값을 돌려줌
  }

  // 🔥 Setter - name 데이터 넣기
  public void setName(String name) {
    this.name = name;  // name 값을 설정
  }
}
