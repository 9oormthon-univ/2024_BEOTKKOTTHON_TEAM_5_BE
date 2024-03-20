package io.festival.distance.domain.waiting.dto;

import io.festival.distance.domain.member.entity.Member;
import lombok.Builder;

@Builder
public record ChatWaitingDto(String myRoomName,
                             Long loveSenderId,
                             Long loveReceiverId,
                             String memberCharacter,
                             Long waitingRoomId) {
}
