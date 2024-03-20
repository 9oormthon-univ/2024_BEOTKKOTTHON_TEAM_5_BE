package io.festival.distance.domain.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class RoomName extends BaseTimeEntity{
    @Column(name = "my_room_name")
    private String myRoomName;

    @Column(name = "opponent_room_name")
    private String opponentRoomName;
}

