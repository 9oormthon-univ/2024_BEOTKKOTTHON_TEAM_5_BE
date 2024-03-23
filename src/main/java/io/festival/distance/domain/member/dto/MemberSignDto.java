package io.festival.distance.domain.member.dto;

import org.springframework.lang.NonNull;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import java.util.List;
import java.util.Objects;

/** NOTE
 * 불변 객체를 생성하기 위해 record 사용
 */
public record MemberSignDto(
        String schoolEmail,
        String loginId,
        String password,
        String checkPassword,
        String gender,
        String telNum,
        String school,
        String college,
		String department,
		String mbti,
		String memberCharacter,
		List<MemberTagDto> memberTagDto,
		List<MemberHobbyDto> memberHobbyDto
		) {
}
