package io.festival.distance.domain.conversation.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChatMessageDto {
    private String chatMessage;
    private Long senderId;
    private Long receiverId;
}
