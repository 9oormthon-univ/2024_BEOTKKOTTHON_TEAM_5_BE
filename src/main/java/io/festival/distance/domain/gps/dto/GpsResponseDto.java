package io.festival.distance.domain.gps.dto;

import lombok.Builder;

@Builder
public record GpsResponseDto (Long memberId, double longitude, double latitude){ }
