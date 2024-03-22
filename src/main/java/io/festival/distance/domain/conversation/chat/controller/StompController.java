package io.festival.distance.domain.conversation.chat.controller;

import io.festival.distance.domain.conversation.chat.dto.ChatMessageDto;
import io.festival.distance.domain.conversation.chat.dto.ChatMessageResponseDto;
import io.festival.distance.domain.conversation.chat.service.ChatMessageService;
import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import io.festival.distance.domain.conversation.chatroom.service.ChatRoomService;
import io.festival.distance.domain.conversation.chatroomsession.entity.ChatRoomSession;
import io.festival.distance.domain.conversation.chatroomsession.service.ChatRoomSessionService;
import io.festival.distance.domain.conversation.roommember.service.RoomMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class StompController {
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    private final ChatRoomSessionService chatRoomSessionService;
    private final RoomMemberService roomMemberService;
    @MessageMapping("/chat/{roomId}") //app/chat/{roomId}로 요청이 들어왔을 때 -> 발신
    @SendTo("/topic/chatroom/{roomId}") // Subscription URL -> 수신
    public ResponseEntity<ChatMessageResponseDto> sendMessage(@DestinationVariable Long roomId,
                                                              @RequestBody ChatMessageDto chatMessageDto) {
        ChatRoom chatRoom = chatRoomService.findRoom(roomId);
        Long chatMessageId = chatMessageService.createMessage(chatRoom, chatMessageDto, principal.getName());

        // receiver 에게 PUSH 알림 전송
        String messageContent = chatMessageDto.getChatMessage();
        Long senderId = chatMessageDto.getSenderId();
        Long receiverId = chatMessageDto.getReceiverId();
        chatMessageService.sendNotificationIfReceiverNotInChatRoom(senderId, receiverId, chatRoom, messageContent);

        // 채팅방 새션 조회
        List<ChatRoomSession> sessionByChatRoom = chatRoomSessionService.findSessionByChatRoom(chatRoom); //2개가 나올 듯?
        // 채팅 읽음 갱신
        for(ChatRoomSession chatRoomSession :sessionByChatRoom){
            Long memberId = chatRoomSession.getMemberId();
            roomMemberService.updateLastMessage(memberId,chatMessageId,roomId); //가장 최근에 읽은 메시지 수정
        }
        return ResponseEntity.ok(chatMessageService.generateMessage(chatMessageId,sessionByChatRoom.size())); //이걸 전달 => 맞다면 새로운 dto사용해서 가공 값 전달
    }
}
