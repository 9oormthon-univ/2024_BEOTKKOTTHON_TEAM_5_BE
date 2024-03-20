package io.festival.distance.domain.conversation.roommember.entity;

import io.festival.distance.domain.base.RoomName;
import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import io.festival.distance.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name = "roommember")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuperBuilder
public class RoomMember extends RoomName { //채팅방 참여자
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_member_id")
    private Long roomMemberId;

    @Column(name = "last_read_message_id")
    private Long lastReadMessageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void updateMessageId(Long chatMessageId){
        this.lastReadMessageId=chatMessageId;
    }
}
