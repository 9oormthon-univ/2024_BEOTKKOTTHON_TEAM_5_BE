package io.festival.distance.domain.conversation.chatroom.dto;

import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChatRoomInfoDto {
    private Long chatRoomId;
    private LocalDateTime createDt;
    private LocalDateTime modifyDt;
    private String roomName;
    private Long opponentMemberId;
    private String memberCharacter;
    public ChatRoomInfoDto(ChatRoom chatRoom) {
        this.chatRoomId = chatRoom.getChatRoomId();
        this.createDt = chatRoom.getCreateDt();
        this.modifyDt = chatRoom.getModifyDt();
        this.roomName = chatRoom.getRoomName();
    }
}
