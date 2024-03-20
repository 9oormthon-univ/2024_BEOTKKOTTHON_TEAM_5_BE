package io.festival.distance.domain.conversation.chatroom.validroomcount;

import io.festival.distance.domain.conversation.roommember.repository.RoomMemberRepository;
import io.festival.distance.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidOpponentRoom {
    private final RoomMemberRepository roomMemberRepository;

    public Long checkOpponentRoom(Member member){
        return roomMemberRepository.countByMember(member);
    }
}
