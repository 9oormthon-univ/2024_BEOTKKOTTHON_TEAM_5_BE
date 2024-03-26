package io.festival.distance.domain.conversation.roommember.repository;


import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import io.festival.distance.domain.conversation.chatroomsession.entity.ChatRoomSession;
import io.festival.distance.domain.conversation.roommember.entity.RoomMember;
import io.festival.distance.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.sound.midi.MetaMessage;
import java.util.List;
import java.util.Optional;

public interface RoomMemberRepository extends JpaRepository<RoomMember, Long> {
    RoomMember findByMemberAndChatRoom(Member member, ChatRoom chatRoom);
    List<RoomMember> findAllByMember(Member member);
    Long countByMember(Member member);
    Long countByChatRoom(ChatRoom chatRoom);

    void deleteByChatRoomAndMember(ChatRoom chatRoom,Member member);

    boolean existsByMemberAndChatRoom(Member member,ChatRoom chatRoom);

    Integer countByChatRoomAndLastReadMessageIdGreaterThan(ChatRoom chatRoom,
                                                                    Long lastMessageId);
}
