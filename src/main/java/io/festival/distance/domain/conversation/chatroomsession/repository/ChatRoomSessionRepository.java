package io.festival.distance.domain.conversation.chatroomsession.repository;

import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import io.festival.distance.domain.conversation.chatroomsession.entity.ChatRoomSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomSessionRepository extends JpaRepository<ChatRoomSession,Long> {

    boolean existsByMemberIdAndChatRoom(Long memberId, ChatRoom chatRoom);
    List<ChatRoomSession> findByChatRoom(ChatRoom chatRoom);

    ChatRoomSession findByMemberIdAndChatRoom(Long memberId, ChatRoom chatRoom);
    Optional<ChatRoomSession> findBySessionName(String sessionName);

}
