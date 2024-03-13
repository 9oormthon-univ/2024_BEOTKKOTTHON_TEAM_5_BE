package io.festival.distance.domain.member.repository;

import io.festival.distance.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findOneWithAuthoritiesByLoginId(String loginId);

    boolean existsBySchoolEmail(String schoolEmail);

    boolean existsByLoginId(String loginId);

    Member findBySchoolEmail(String schoolEmail);
}
