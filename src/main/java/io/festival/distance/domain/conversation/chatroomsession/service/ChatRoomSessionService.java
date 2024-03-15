package io.festival.distance.domain.conversation.chatroomsession.service;

import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import io.festival.distance.domain.conversation.chatroom.service.ChatRoomService;
import io.festival.distance.domain.conversation.chatroomsession.entity.ChatRoomSession;
import io.festival.distance.domain.conversation.chatroomsession.repository.ChatRoomSessionRepository;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ChatRoomSessionService {
    private final ChatRoomSessionRepository chatRoomSessionRepository;
    private final ChatRoomService chatRoomService;

    @Transactional
    public void createChatRoomSession(Long chatRoomId, Long memberId, String sessionName){
        ChatRoom room = chatRoomService.findRoom(chatRoomId);
        ChatRoomSession session = chatRoomSessionRepository.findByMemberIdAndChatRoom(memberId, room);
        if(!Objects.isNull(session)){
           deleteChatRoomSession(session);
        }
        ChatRoomSession roomSession = ChatRoomSession.builder()
                .memberId(memberId)
                .chatRoom(room)
                .sessionName(sessionName)
                .build();
        chatRoomSessionRepository.save(roomSession);
    }

    @Transactional
    public void deleteChatRoomSession(ChatRoomSession chatRoomSession){
        chatRoomSessionRepository.delete(chatRoomSession);
    }

    public List<ChatRoomSession> findSessionByChatRoom(ChatRoom chatRoom){
        return chatRoomSessionRepository.findByChatRoom(chatRoom);
    }

    public ChatRoomSession findSession(String sessionName){
        return chatRoomSessionRepository.findBySessionName(sessionName)
                .orElseThrow(()-> new DistanceException(ErrorCode.NOT_EXIST_MEMBER));
    }
}
