package io.festival.distance.domain.member.validsignup;

import io.festival.distance.domain.member.dto.MemberSignDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidSignup {
    private final ValidLoginId validLoginId;
    private final ValidSchoolEmail validSchoolEmail;

    public boolean validationSignup(MemberSignDto signDto){
        return validSchoolEmail.duplicateCheckSchoolEmail(signDto.schoolEmail())
                && validLoginId.duplicateCheckLoginId(signDto.loginId());
    }
}
