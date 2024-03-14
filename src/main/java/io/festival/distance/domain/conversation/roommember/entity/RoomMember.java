package io.festival.distance.domain.conversation.roommember.entity;

import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import io.festival.distance.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "roommember")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class RoomMember { //채팅방 참여자
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_member_id")
    private Long roomMemberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
