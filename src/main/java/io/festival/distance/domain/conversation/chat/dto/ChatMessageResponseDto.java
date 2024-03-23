package io.festival.distance.domain.conversation.chat.dto;

import io.festival.distance.domain.conversation.chat.entity.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChatMessageResponseDto {
    private String chatMessage;
    private Long senderId;
    private String senderName; //닉네임
    private int unreadCount;
    private Long messageId;
    private LocalDateTime sendDt;
    private boolean checkTiKiTaKa;

    public ChatMessageResponseDto(ChatMessage message) {
        this.messageId=message.getChatMessageId();
        this.chatMessage = message.getChatMessage();
        this.senderId = message.getSenderId();
        this.senderName = message.getSenderName();
        this.unreadCount = message.getUnreadCount();
        this.sendDt = message.getCreateDt();
    }
}
