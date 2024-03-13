package io.festival.distance.domain.member.valid;

import io.festival.distance.domain.member.repository.MemberRepository;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
import io.festival.distance.exception.ErrorResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidNickname {
    private final MemberRepository memberRepository;

    public boolean duplicateCheckNickname(String nickName){
        if(memberRepository.existsByNickName(nickName))
            throw new DistanceException(ErrorCode.EXIST_NICKNAME);
        return true;
    }
}
