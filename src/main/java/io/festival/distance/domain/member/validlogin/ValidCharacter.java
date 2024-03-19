package io.festival.distance.domain.member.validlogin;

import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ValidCharacter {
    public boolean checkMemberCharacter(Member member){
        return Objects.isNull(member.getMemberCharacter());
    }
}
