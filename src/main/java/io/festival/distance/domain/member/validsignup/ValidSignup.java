package io.festival.distance.domain.member.validsignup;

import io.festival.distance.domain.member.dto.MemberSignDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidSignup {
    private final ValidLoginId validLoginId;

    public boolean validationSignup(MemberSignDto signDto){
        return validLoginId.duplicateCheckLoginId(signDto.loginId());
    }

    public boolean validationLoginId(String loginId){
        return validLoginId.duplicateCheckLoginId(loginId);
    }
}
