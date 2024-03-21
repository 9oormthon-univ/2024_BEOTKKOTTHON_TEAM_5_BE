package io.festival.distance.domain.conversation.chat.service;

import io.festival.distance.domain.conversation.chat.dto.ChatMessageDto;
import io.festival.distance.domain.conversation.chat.dto.ChatMessageResponseDto;
import io.festival.distance.domain.conversation.chat.entity.ChatMessage;
import io.festival.distance.domain.conversation.chat.repository.ChatMessageRepository;
import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import io.festival.distance.domain.conversation.chatroomsession.entity.ChatRoomSession;
import io.festival.distance.domain.conversation.chatroomsession.repository.ChatRoomSessionRepository;
import io.festival.distance.domain.conversation.roommember.entity.RoomMember;
import io.festival.distance.domain.conversation.roommember.service.RoomMemberService;
import io.festival.distance.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomSessionRepository chatRoomSessionRepository;
    private final RoomMemberService roomMemberService;

    @Transactional
    public Long createMessage(ChatRoom chatRoom, ChatMessageDto chatMessageDto) {
        ChatMessage message = ChatMessage.builder()
                .senderId(chatMessageDto.getSenderId())
                .chatMessage(chatMessageDto.getChatMessage())
                .unreadCount(2)
                .chatRoom(chatRoom)
                .build();

        // 채팅방에 받는 사람 있는지 확인 먼저
        if(!chatRoomSessionRepository.existsByMemberIdAndChatRoom(chatMessageDto.getSenderId(), chatRoom)){
            System.out.println("채팅방에 받는 사람이 없으므로 알림을 보내야합니다.");
        //     //알람 로직
        }
        return chatMessageRepository.save(message).getChatMessageId();
    }

    @Transactional
    public ChatMessageResponseDto generateMessage(Long chatMessageId,int currentMemberCount) {
        ChatMessage chatMessage = chatMessageRepository.findById(chatMessageId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 메시지"));
        chatMessage.readCountUpdate(currentMemberCount);
        return ChatMessageResponseDto.builder()
                .chatMessage(chatMessage.getChatMessage())
                .senderName(chatMessage.getSenderName())
                .senderId(chatMessage.getSenderId())
                .unreadCount(chatMessage.getUnreadCount())
                .sendDt(chatMessage.getCreateDt())
                .build();
    }

    @Transactional
    public List<ChatMessageResponseDto> markAllMessagesAsRead(ChatRoom chatRoom, Member member) {
        RoomMember roomMember = roomMemberService.findRoomMember(member,chatRoom); //방금 들어온 멤버가
        Long lastChatMessageId=roomMember.getLastReadMessageId(); //가장 나중에 읽은 메시지 PK값

        List<ChatMessage> messages = chatMessageRepository.findByChatRoomAndChatMessageIdGreaterThan(chatRoom, lastChatMessageId);
        messages.forEach(message ->{
            message.readCountUpdate(1);
            chatMessageRepository.save(message);
        });

        List<ChatMessageResponseDto> responseDtoList = messages.stream()
                .map(ChatMessageResponseDto::new)
                .collect(Collectors.toList());

        if(!responseDtoList.isEmpty()){ //최신 메시지가 있다면
            roomMember.updateMessageId(responseDtoList.get(responseDtoList.size()-1).getMessageId());
        }
        return responseDtoList;
    }
}
