package io.festival.distance.domain.conversation.chatroom.repository;


import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {
}
