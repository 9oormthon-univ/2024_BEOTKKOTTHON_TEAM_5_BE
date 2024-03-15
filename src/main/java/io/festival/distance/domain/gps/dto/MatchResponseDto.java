package io.festival.distance.domain.gps.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record MatchResponseDto (List<MatchUserDto> matchedUsers) { }
