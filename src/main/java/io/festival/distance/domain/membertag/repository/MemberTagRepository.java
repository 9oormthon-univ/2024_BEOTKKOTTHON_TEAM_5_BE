package io.festival.distance.domain.membertag.repository;

import io.festival.distance.domain.membertag.entity.MemberTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberTagRepository extends JpaRepository<MemberTag,Long> {
}
