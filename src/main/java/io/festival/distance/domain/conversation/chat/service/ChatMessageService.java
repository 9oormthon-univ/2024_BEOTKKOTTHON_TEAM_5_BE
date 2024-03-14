package io.festival.distance.domain.conversation.chat.service;

import io.festival.distance.domain.conversation.chat.dto.ChatMessageDto;
import io.festival.distance.domain.conversation.chat.dto.ChatMessageResponseDto;
import io.festival.distance.domain.conversation.chat.entity.ChatMessage;
import io.festival.distance.domain.conversation.chat.repository.ChatMessageRepository;
import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;

    @Transactional
    public Long createMessage(ChatRoom chatRoom, ChatMessageDto chatMessageDto) {
        ChatMessage message = ChatMessage.builder()
                .senderId(chatMessageDto.getSenderId())
                .chatMessage(chatMessageDto.getChatMessage())
                .unreadCount(1)
                .chatRoom(chatRoom)
                .build();

        return chatMessageRepository.save(message).getChatMessageId();
    }

    public ChatMessageResponseDto generateMessage(Long chatMessageId) {
        ChatMessage chatMessage = chatMessageRepository.findById(chatMessageId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 메시지"));
        return ChatMessageResponseDto.builder()
                .chatMessage(chatMessage.getChatMessage())
                .senderName(chatMessage.getSenderName())
                .senderId(chatMessage.getSenderId())
                .sendDt(chatMessage.getCreateDt())
                .build();
    }
}
