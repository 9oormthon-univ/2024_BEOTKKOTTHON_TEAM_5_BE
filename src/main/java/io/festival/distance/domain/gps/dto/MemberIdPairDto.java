package io.festival.distance.domain.gps.dto;

import lombok.Builder;

@Builder
public record MemberIdPairDto(long id1, long id2) {
}
