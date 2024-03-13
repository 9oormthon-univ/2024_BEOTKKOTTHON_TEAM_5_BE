package io.festival.distance.domain.member.dto;

import java.util.List;

/** NOTE
 * 불변 객체를 생성하기 위해 record 사용
 */
public record MemberInfoDto(String mbti,
                            String memberCharacter,
                            List<MemberTagDto> memberTagDto) {
}
