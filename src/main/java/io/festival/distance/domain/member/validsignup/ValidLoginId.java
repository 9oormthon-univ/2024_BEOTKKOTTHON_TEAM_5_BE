package io.festival.distance.domain.member.validsignup;

import io.festival.distance.domain.member.repository.MemberRepository;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ValidLoginId {
    private final MemberRepository memberRepository;

    public boolean duplicateCheckLoginId(String loginId){
        if(Objects.isNull(loginId))
            throw new DistanceException(ErrorCode.NOT_NULL_NICKNAME);
        if(memberRepository.existsByLoginId(loginId))
            throw new DistanceException(ErrorCode.EXIST_NICKNAME);
        return true;
    }
}
