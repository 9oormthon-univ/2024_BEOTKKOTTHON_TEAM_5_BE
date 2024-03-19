package io.festival.distance.domain.conversation.chatroom.repository;


import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import io.festival.distance.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {
    List<ChatRoom> findAllByMember(Member member);
}
