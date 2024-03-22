package io.festival.distance.domain.conversation.chat.repository;

import io.festival.distance.domain.conversation.chat.entity.ChatMessage;
import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long> {
    List<ChatMessage> findByChatRoomAndChatMessageIdGreaterThan(ChatRoom chatRoom,Long messageId);
    List<ChatMessage> findByChatRoom(ChatRoom chatRoom);
    List<ChatMessage> findByChatRoomAndSenderId(ChatRoom chatRoom, Long memberId);
    ChatMessage findTop1ByChatRoomOrderByCreateDtDesc(ChatRoom chatRoom);
}
