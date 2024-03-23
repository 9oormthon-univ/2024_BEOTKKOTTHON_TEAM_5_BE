package io.festival.distance.domain.conversation.chat.repository;

import io.festival.distance.domain.conversation.chat.entity.ChatMessage;
import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import org.hibernate.jpa.internal.ManagedFlushCheckerLegacyJpaImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long> {
    List<ChatMessage> findByChatRoomAndChatMessageIdGreaterThan(ChatRoom chatRoom,Long messageId);
    List<ChatMessage> findByChatRoom(ChatRoom chatRoom);
    ChatMessage findTop1ByChatRoomOrderByCreateDtDesc(ChatRoom chatRoom);

    @Query(value = "SELECT COUNT(*) FROM (" +
            "SELECT sender_id, LAG(sender_id) OVER (ORDER BY create_dt) AS prev_sender_id FROM chatmessage WHERE chatroom_id =:chatRoomId) AS OrderedMessages " +
            "WHERE sender_id != prev_sender_id", nativeQuery = true)
    Long checkTiKiTaKa(@Param("chatRoomId") ChatRoom  chatRoom);
}
