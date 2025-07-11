package dev.mapofdev.repository;

import dev.mapofdev.domain.Member;

import java.util.*;

// 메모리에서 HashMap을 사용해 회원 정보를 빠르게 저장하고 조회하는 저장소 클래스
public class MemoryMemberRepository implements MemberRepository {

  // 회원번호를 키로 하여 회원 객체를 저장하는 메모리 저장소
  private static Map<Long,Member> store = new HashMap<>();

  // 새로운 회원에게 고유한 번호를 자동으로 부여하기 위한 순번 카운터
  private static long sequence = 0L;

  @Override
  // 새 회원에게 고유 ID를 부여한 후 저장소에 보관하고 결과를 반환하는 메소드
  public Member save(Member member) {
    member.setId(++sequence); // 순번을 1 증가시켜 새 회원의 고유 ID로 설정
    store.put(member.getId(), member); // 회원번호를 키로 하여 회원 객체를 저장소에 보관
    return member; // 저장이 완료된 회원 객체를 호출자에게 반환
  }

  @Override
  // 주어진 ID로 회원을 찾되 존재하지 않을 경우를 대비해 Optional로 안전하게 반환하는 메소드
  public Optional<Member> findById(String id) {
    return Optional.ofNullable(store.get(id)); // 해당 ID의 회원을 찾아 null 안전하게 Optional로 반환
  }

  @Override
  // 저장된 모든 회원 중에서 주어진 이름과 일치하는 첫 번째 회원을 찾아 반환하는 메소드
  public Optional<Member> findByName(String name) {
    return store.values().stream() // 저장된 모든 회원들을 하나씩 검사하여
      .filter(member -> member.getName().equals(name)) // 이름이 일치하는 회원만 필터링하고
      .findFirst(); // 그 중 첫 번째 회원을 Optional로 반환
  }

  @Override
  // 저장된 모든 회원 정보를 새로운 리스트로 복사해서 외부 수정을 방지하며 반환하는 메소드
  public List<Member> findAll() {
    return new ArrayList<>(store.values()); // 원본 데이터 보호를 위해 새 ArrayList에 복사하여 반환
  }
}
