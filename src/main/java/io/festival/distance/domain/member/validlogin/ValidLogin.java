package io.festival.distance.domain.member.validlogin;

import io.festival.distance.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidLogin {
    private final ValidCharacter validCharacter;
    private final ValidMbti validMbti;

    public boolean checkLogin(Member member){
        return (validCharacter.checkMemberCharacter(member)||validMbti.checkMbti(member));
    }
}
