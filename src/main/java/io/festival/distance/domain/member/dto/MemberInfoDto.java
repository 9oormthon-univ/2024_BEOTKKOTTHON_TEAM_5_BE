package io.festival.distance.domain.member.dto;

import lombok.Builder;

import java.util.List;

/** NOTE
 * 불변 객체를 생성하기 위해 record 사용
 */
@Builder
public record MemberInfoDto(String mbti,
                            String memberCharacter,
                            String department,
                            List<MemberTagDto> memberTagDto,
                            List<MemberHobbyDto> memberHobbyDto) {
}
