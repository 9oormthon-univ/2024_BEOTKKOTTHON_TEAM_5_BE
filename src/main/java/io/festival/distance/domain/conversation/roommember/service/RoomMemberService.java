package io.festival.distance.domain.conversation.roommember.service;

import io.festival.distance.domain.conversation.chat.entity.ChatMessage;
import io.festival.distance.domain.conversation.chat.repository.ChatMessageRepository;
import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import io.festival.distance.domain.conversation.chatroom.service.ChatRoomService;
import io.festival.distance.domain.conversation.roommember.entity.RoomMember;
import io.festival.distance.domain.conversation.roommember.repository.RoomMemberRepository;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class RoomMemberService {
    private final RoomMemberRepository roomMemberRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MemberService memberService;
    private final ChatRoomService chatRoomService;

    @Transactional
    public void updateLastMessage(Long memberId,Long chatMessageId,Long roomId){
        Member member = memberService.findMember(memberId);
        ChatRoom chatRoom = chatRoomService.findRoom(roomId);
        RoomMember roomMember = roomMemberRepository.findByMemberAndChatRoom(member,chatRoom);
        roomMember.updateMessageId(chatMessageId);
    }

    public RoomMember findRoomMember(Member member,ChatRoom chatRoom){
        return roomMemberRepository.findByMemberAndChatRoom(member,chatRoom);
    }

    @Transactional
    public void goOutRoom(Long chatRoomId, Principal principal) {
        ChatRoom chatRoom = chatRoomService.findRoom(chatRoomId);
        Member member = memberService.findByLoginId(principal.getName());
        if(roomMemberRepository.countByMember(member)==1){
            roomMemberRepository.deleteByChatRoomAndMember(chatRoom,member);
            chatRoomService.delete(chatRoomId);
            return;
        }
        ChatMessage leaveMessage = ChatMessage.builder()
                .chatMessage(member.getNickName() + "님이 나갔습니다.")
                .chatRoom(chatRoom)
                .build();
        roomMemberRepository.deleteByChatRoomAndMember(chatRoom,member);
        chatMessageRepository.save(leaveMessage);
    }
}
