package io.festival.distance.domain.conversation.chatroom.validroomcount;

import io.festival.distance.domain.conversation.roommember.repository.RoomMemberRepository;
import io.festival.distance.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ValidExistRoom {
    private final RoomMemberRepository roomMemberRepository;

    public Optional<Long> ExistRoom(Member me, Member opponent) {
        return roomMemberRepository.findAllByMember(me)
                .stream()
                .filter(roomMember ->
                    roomMemberRepository.existsByMemberAndChatRoom(opponent,roomMember.getChatRoom()))
                .map(roomMember -> roomMember.getChatRoom().getChatRoomId())
                .findFirst();
    }
}
