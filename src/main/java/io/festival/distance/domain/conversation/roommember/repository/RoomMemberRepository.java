package io.festival.distance.domain.conversation.roommember.repository;


import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import io.festival.distance.domain.conversation.roommember.entity.RoomMember;
import io.festival.distance.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomMemberRepository extends JpaRepository<RoomMember, Long> {
    Optional<RoomMember> findByChatRoomAndMember(ChatRoom chatRoom, Member member);
    RoomMember findByChatRoom(ChatRoom chatRoom);
    RoomMember findByMember(Member member);
}
