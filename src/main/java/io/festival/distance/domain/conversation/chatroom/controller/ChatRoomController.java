package io.festival.distance.domain.conversation.chatroom.controller;

import io.festival.distance.domain.conversation.chat.entity.ChatMessage;
import io.festival.distance.domain.conversation.chatroom.dto.ChatRoomDto;
import io.festival.distance.domain.conversation.chatroom.dto.ChatRoomInfoDto;
import io.festival.distance.domain.conversation.chatroom.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatroom")
@CrossOrigin
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @PostMapping("/create")
    public ResponseEntity<ChatRoomDto> createRoom(@RequestBody ChatRoomDto chatRoomDto, Principal principal){
        return ResponseEntity.ok(chatRoomService.generateRoom(chatRoomDto,principal));
    }

    @GetMapping
    public ResponseEntity<List<ChatRoomInfoDto>> showAllRoom(){
        return ResponseEntity.ok(chatRoomService.findAllRoom());
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<List<ChatMessage>> getChatMessage(@PathVariable Long roomId){
        return ResponseEntity.ok(chatRoomService.getMessage(roomId));
    }

    @DeleteMapping("/delete/{roomId}")
    public ResponseEntity<Long> deleteRoom(@PathVariable Long roomId){
        return ResponseEntity.ok(chatRoomService.delete(roomId));
    }
}
