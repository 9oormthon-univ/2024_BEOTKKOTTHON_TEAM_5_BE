package io.festival.distance.domain.conversation.chat.dto;

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
    private String senderName;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalDateTime sendDt;
}
