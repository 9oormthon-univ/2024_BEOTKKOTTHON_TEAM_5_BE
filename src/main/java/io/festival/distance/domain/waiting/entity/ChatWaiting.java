package io.festival.distance.domain.waiting.entity;

import io.festival.distance.domain.base.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "chat_waiting")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ChatWaiting extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="waiting_id")
    private Long waitingId;


}
