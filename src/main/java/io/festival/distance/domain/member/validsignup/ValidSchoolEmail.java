package io.festival.distance.domain.member.validsignup;

import io.festival.distance.domain.member.repository.MemberRepository;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidSchoolEmail {
    private final MemberRepository memberRepository;

    public boolean duplicateCheckSchoolEmail(String schoolEmail){
        if(memberRepository.existsBySchoolEmail(schoolEmail))
            throw new DistanceException(ErrorCode.EXIST_EMAIL);
        return true;
    }
}
