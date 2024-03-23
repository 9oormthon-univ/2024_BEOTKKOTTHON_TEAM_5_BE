package io.festival.distance.domain.member.validsignup;

import io.festival.distance.domain.member.dto.MemberHobbyDto;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class ValidHobby {
	public void notNullHobby(List<MemberHobbyDto> hobbies){
		if(Objects.isNull(hobbies))
			throw new DistanceException(ErrorCode.NOT_NULL_HOBBY);
	}
}
