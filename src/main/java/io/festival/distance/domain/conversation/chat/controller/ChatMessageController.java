package io.festival.distance.domain.conversation.chat.controller;

import io.festival.distance.domain.conversation.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/chatmessages")
public class ChatMessageController {
    private final ChatMessageService chatMessageService;

}
