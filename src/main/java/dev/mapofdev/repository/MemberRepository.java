package dev.mapofdev.repository;

import dev.mapofdev.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
  Member save(Member member);
  Optional<Member> findById(String id);
  Optional<Member> findByName(String name);
  List<Member> findAll();
}
