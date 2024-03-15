package io.festival.distance.domain.gps.dto;

import java.util.List;

import io.festival.distance.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class matchResponseDto {
	private List<Member> matchedUsers;
}
