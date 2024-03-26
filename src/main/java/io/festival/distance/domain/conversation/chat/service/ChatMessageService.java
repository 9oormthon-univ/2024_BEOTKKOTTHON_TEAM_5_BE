package io.festival.distance.domain.conversation.chat.service;

import io.festival.distance.domain.conversation.chat.dto.ChatMessageDto;
import io.festival.distance.domain.conversation.chat.dto.ChatMessageResponseDto;
import io.festival.distance.domain.conversation.chat.entity.ChatMessage;
import io.festival.distance.domain.conversation.chat.repository.ChatMessageRepository;
import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import io.festival.distance.domain.conversation.chatroomsession.repository.ChatRoomSessionRepository;
import io.festival.distance.domain.conversation.roommember.entity.RoomMember;
import io.festival.distance.domain.conversation.roommember.service.RoomMemberService;
import io.festival.distance.domain.firebase.service.FCMService;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.MemberService;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
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
    private final RoomMemberService roomMemberService;
    private final MemberService memberService;
    private final FCMService fcmService;

    @Transactional
    public Long createMessage(ChatRoom chatRoom, ChatMessageDto chatMessageDto) {
        Member member = memberService.findMember(chatMessageDto.getReceiverId()); //나
        if(chatMessageDto.getChatMessage().isEmpty())
            return null;

        ChatMessage message = ChatMessage.builder()
                .senderId(chatMessageDto.getSenderId())
                .chatMessage(chatMessageDto.getChatMessage())
                .senderName(member.getNickName())
                .unreadCount(2)
                .chatRoom(chatRoom)
                .build();

        return chatMessageRepository.save(message).getChatMessageId();
    }

    @Transactional(readOnly = true)
    public boolean checkTiKiTaKa(ChatRoom chatRoom){
        return chatMessageRepository.checkTiKiTaKa(chatRoom)>=10;
    }

    @Transactional
    public void sendNotificationIfReceiverNotInChatRoom(Long senderId, Long receiverId, ChatRoom chatRoom, String chatMessage){
        // 채팅방에 받는 사람 있는지 확인
        // if(!chatRoomSessionRepository.existsByMemberIdAndChatRoom(senderId, chatRoom)){
        //
        // }
        // 알림을 보낼 떄 필요한 값들
        Member sender = memberService.findMember(senderId); //받는 사람
        String receiverNickName = memberService.findMember(receiverId).getNickName(); // 발신자의 닉네임
        // FCM 알림 전송 발송자 닉네임이, chatMessage를 특정 clietnToken에게
        String clientToken = sender.getClientToken();
        if (clientToken != null) { // clientToken이 null이 아닐 때만 FCM 알림 전송
            fcmService.sendNotification(clientToken, receiverNickName, chatMessage);
        } else {
            System.out.println("client 토큰이 없어서 알림을 안 보냈습니다.");
        }
    }

    /** TODO
     * 메소드 네이밍 변경 필요!
     */
    @Transactional
    public ChatMessageResponseDto generateMessage(Long chatMessageId,int currentMemberCount,ChatRoom chatRoom) {
        ChatMessage chatMessage = chatMessageRepository.findById(chatMessageId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 메시지"));
        chatMessage.readCountUpdate(currentMemberCount);

        return ChatMessageResponseDto.builder()
                .chatMessage(chatMessage.getChatMessage())
                .senderName(chatMessage.getSenderName())
                .senderId(chatMessage.getSenderId())
                .unreadCount(chatMessage.getUnreadCount())
                .sendDt(chatMessage.getCreateDt())
                .checkTiKiTaKa(checkTiKiTaKa(chatRoom))
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

        // 현재 채팅방에 들어온 사람의 가장 최근에 읽은 곳까지 unReadCount 갱신 (다시 접속했는데 새로운 메세지가 없는 경우)
        if(!responseDtoList.isEmpty()){ //최신 메시지가 있다면
            roomMember.updateMessageId(responseDtoList.get(responseDtoList.size()-1).getMessageId());
        }

        return responseDtoList;
    }
}
