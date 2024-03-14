package io.festival.distance.domain.conversation.chat.controller;

import io.festival.distance.domain.conversation.chat.dto.ChatMessageDto;
import io.festival.distance.domain.conversation.chat.dto.ChatMessageResponseDto;
import io.festival.distance.domain.conversation.chat.service.ChatMessageService;
import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import io.festival.distance.domain.conversation.chatroom.service.ChatRoomService;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class StompController {
    private final ChatRoomService chatRoomService;
    private final MemberService memberService;
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat/{roomId}") //app/chat/{roomId}로 요청이 들어왔을 때 -> 발신
    @SendTo("/topic/api/chatroom/{roomId}") // Subscription URL -> 수신
    public ResponseEntity<ChatMessageResponseDto> sendMessage(@DestinationVariable Long roomId,
                                                              @RequestBody ChatMessageDto chatMessageDto) {
        ChatRoom chatRoom = chatRoomService.findRoom(roomId);
        Long chatMessageId = chatMessageService.createMessage(chatRoom, chatMessageDto);
        return ResponseEntity.ok(chatMessageService.generateMessage(chatMessageId)); //이걸 전달 => 맞다면 새로운 dto사용해서 가공 값 전달
    }
}
