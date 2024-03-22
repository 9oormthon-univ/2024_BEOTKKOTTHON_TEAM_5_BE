package io.festival.distance.domain.declare.repository;

import io.festival.distance.domain.declare.entity.Declare;
import io.festival.distance.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeclareRepository extends JpaRepository<Declare,Long> {
    boolean existsByMeAndOpponent(Member me,Member opponent);
}
