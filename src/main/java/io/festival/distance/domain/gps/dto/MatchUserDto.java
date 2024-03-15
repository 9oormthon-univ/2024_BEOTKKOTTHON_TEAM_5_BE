package io.festival.distance.domain.gps.dto;

import lombok.Builder;

@Builder
public record MatchUserDto (long memberId, String mbti, String nickName, String department, String memberCharacter) { }
