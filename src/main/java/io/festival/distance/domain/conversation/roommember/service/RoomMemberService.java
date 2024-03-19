package io.festival.distance.domain.conversation.roommember.service;

import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import io.festival.distance.domain.conversation.chatroom.service.ChatRoomService;
import io.festival.distance.domain.conversation.roommember.entity.RoomMember;
import io.festival.distance.domain.conversation.roommember.repository.RoomMemberRepository;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoomMemberService {
    private final RoomMemberRepository roomMemberRepository;
    private final MemberService memberService;
    private final ChatRoomService chatRoomService;

    @Transactional
    public void updateLastMessage(Long memberId,Long chatMessageId,Long roomId){
        Member member = memberService.findMember(memberId);
        ChatRoom chatRoom = chatRoomService.findRoom(roomId);
        RoomMember roomMember = roomMemberRepository.findByMemberAndChatRoom(member,chatRoom);
        roomMember.updateMessageId(chatMessageId);
    }

    public RoomMember findRoomMember(Member member){
        return roomMemberRepository.findByMember(member);
    }
}
