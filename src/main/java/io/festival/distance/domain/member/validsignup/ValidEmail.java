package io.festival.distance.domain.member.validsignup;

import io.festival.distance.domain.member.repository.MemberRepository;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class ValidEmail {
	private final MemberRepository memberRepository;
	// [영어 또는 숫자 또는 .?%+-] + @ + [영어 또는 숫자.-] + . + [영어 2~6자리]
	private static final Pattern EMAIL_PATTERN =
		Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
	// Null, 이메일 형식인지, 중복된 이메일인지 확인
	public boolean checkValidEmail(String email) {
		if (Objects.isNull(email)) {
			throw new DistanceException(ErrorCode.NOT_NULL_NICKNAME);
		}
		if (!EMAIL_PATTERN.matcher(email).matches()) {
			throw new DistanceException(ErrorCode.INVALID_EMAIL_FORMAT);
		}
		if (memberRepository.existsByLoginId(email)) {
			throw new DistanceException(ErrorCode.EXIST_EMAIL);
		}
		return true;
	}
}
