package io.festival.distance.domain.conversation.chatroom.controller;

import io.festival.distance.domain.conversation.chat.dto.ChatMessageResponseDto;
import io.festival.distance.domain.conversation.chat.entity.ChatMessage;
import io.festival.distance.domain.conversation.chat.service.ChatMessageService;
import io.festival.distance.domain.conversation.chatroom.dto.ChatRoomDto;
import io.festival.distance.domain.conversation.chatroom.dto.ChatRoomInfoDto;
import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import io.festival.distance.domain.conversation.chatroom.service.ChatRoomService;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatroom")
@CrossOrigin
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final MemberService memberService;
    private final ChatMessageService chatMessageService;

    @PostMapping("/create")
    public ResponseEntity<Long> createRoom(@RequestBody ChatRoomDto chatRoomDto, Principal principal){
        return ResponseEntity.ok(chatRoomService.generateRoom(chatRoomDto,principal));
    }

    @GetMapping
    public ResponseEntity<List<ChatRoomInfoDto>> showAllRoom(Principal principal){
        return ResponseEntity.ok(chatRoomService.findAllRoom(principal.getName()));
    }

    @GetMapping("/{chatRoomId}") //채팅방에 들어온 경우
    public ResponseEntity<List<ChatMessageResponseDto>> readMessage(@PathVariable Long chatRoomId, Principal principal){
        System.out.println(principal.getName());
        return ResponseEntity.ok(chatMessageService.markAllMessagesAsRead(chatRoomService.findRoom(chatRoomId),memberService.findByLoginId(principal.getName())));
    }

    @DeleteMapping("/delete/{roomId}")
    public ResponseEntity<Long> deleteRoom(@PathVariable Long roomId){
        return ResponseEntity.ok(chatRoomService.delete(roomId));
    }
}
