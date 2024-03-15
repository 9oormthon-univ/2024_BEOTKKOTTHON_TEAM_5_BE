package io.festival.distance.domain.conversation.roommember.service;

import io.festival.distance.domain.conversation.roommember.entity.RoomMember;
import io.festival.distance.domain.conversation.roommember.repository.RoomMemberRepository;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.MemberService;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoomMemberService {
    private final RoomMemberRepository roomMemberRepository;
    private final MemberService memberService;

    @Transactional
    public void updateLastMessage(Long memberId,Long chatMessageId){
        Member member = memberService.findMember(memberId);
        RoomMember roomMember = roomMemberRepository.findByMember(member);
        roomMember.updateMessageId(chatMessageId);
    }

    public RoomMember findRoomMember(Member member){
        return roomMemberRepository.findByMember(member);
    }
}
