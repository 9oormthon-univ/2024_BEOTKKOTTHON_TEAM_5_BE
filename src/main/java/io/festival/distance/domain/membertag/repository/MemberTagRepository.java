package io.festival.distance.domain.membertag.repository;

import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.memberhobby.repository.MemberHobbyRepository;
import io.festival.distance.domain.membertag.entity.MemberTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberTagRepository extends JpaRepository<MemberTag,Long> {
    List<MemberTag> findAllByMember(Member member);
}
