package io.festival.distance.domain.memberhobby.repository;

import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.memberhobby.entity.MemberHobby;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberHobbyRepository extends JpaRepository<MemberHobby,Long> {
    List<MemberHobby> findAllByMember(Member member);
}
