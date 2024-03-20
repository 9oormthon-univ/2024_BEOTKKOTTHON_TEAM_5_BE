package io.festival.distance.domain.conversation.roommember.controller;

import io.festival.distance.domain.conversation.roommember.service.RoomMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/room-member")
@CrossOrigin
public class RoomMemberController {
    private final RoomMemberService roomMemberService;

    @GetMapping("/leave/{chatRoomId}")
    public ResponseEntity<Void> leaveRoom(@PathVariable Long chatRoomId, Principal principal){
        roomMemberService.goOutRoom(chatRoomId,principal);
        return ResponseEntity.ok().build();
    }
}
