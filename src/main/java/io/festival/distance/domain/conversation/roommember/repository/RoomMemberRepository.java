package io.festival.distance.domain.conversation.roommember.repository;


import io.festival.distance.domain.conversation.roommember.entity.RoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomMemberRepository extends JpaRepository<RoomMember,Long> {
}
