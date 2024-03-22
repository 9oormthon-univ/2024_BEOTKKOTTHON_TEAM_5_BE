package io.festival.distance.domain.gps.dto;

import io.festival.distance.domain.member.dto.MemberInfoDto;
import lombok.Builder;

import java.util.List;

@Builder
public record MatchUserDto (long memberId,
                            String nickName,
                            String department,
                            MemberInfoDto memberInfoDto) {

}
