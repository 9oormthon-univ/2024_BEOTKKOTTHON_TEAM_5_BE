package io.festival.distance.domain.conversation.chatroom.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChatRoomDto {
    private String roomName;
    private Long memberId;
}
