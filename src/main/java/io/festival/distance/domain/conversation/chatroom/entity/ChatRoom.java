package io.festival.distance.domain.conversation.chatroom.entity;

import io.festival.distance.domain.base.BaseTimeEntity;
import io.festival.distance.domain.conversation.roommember.entity.RoomMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chatroom")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ChatRoom extends BaseTimeEntity {  //채팅방
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatroom_id")
    private Long chatRoomId;

    @Column(name = "room_name")
    private String roomName;

    @OneToMany(mappedBy = "chatRoom",cascade = CascadeType.REMOVE)
    private List<RoomMember> member=new ArrayList<>();
}
