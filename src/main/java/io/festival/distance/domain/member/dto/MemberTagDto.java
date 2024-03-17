package io.festival.distance.domain.member.dto;

import lombok.Builder;

/** NOTE
 * 불변 객체를 생성하기 위해 record 사용
 */
@Builder
public record MemberTagDto(String tag) {
}
