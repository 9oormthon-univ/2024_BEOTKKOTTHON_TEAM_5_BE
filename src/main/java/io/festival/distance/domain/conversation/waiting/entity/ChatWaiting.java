package io.festival.distance.domain.conversation.waiting.entity;

import io.festival.distance.domain.base.RoomName;
import io.festival.distance.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name = "chat_waiting")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@SuperBuilder
public class ChatWaiting extends RoomName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "waiting_id")
    private Long waitingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "love_sender")
    private Member loveSender; //발신, 러브콜보내는 사람

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "love_receiver")
    private Member loveReceiver;// 러브콜 받는사람 A
}
