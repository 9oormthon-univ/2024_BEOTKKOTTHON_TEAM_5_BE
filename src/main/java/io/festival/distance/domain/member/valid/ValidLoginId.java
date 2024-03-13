package io.festival.distance.domain.member.valid;

import io.festival.distance.domain.member.repository.MemberRepository;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidLoginId {
    private final MemberRepository memberRepository;

    public boolean duplicateCheckLoginId(String loginId){
        if(memberRepository.existsByLoginId(loginId))
            throw new DistanceException(ErrorCode.EXIST_NICKNAME);
        return true;
    }
}
