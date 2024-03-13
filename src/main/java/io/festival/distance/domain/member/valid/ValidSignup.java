package io.festival.distance.domain.member.valid;

import io.festival.distance.domain.member.dto.MemberSignDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidSignup {
    private final ValidLoginId validLoginId;
    private final ValidSchoolEmail validSchoolEmail;
    private final ValidPassword validPassword;

    public boolean validationSignup(MemberSignDto signDto){
        return validSchoolEmail.duplicateCheckSchoolEmail(signDto.schoolEmail())
                && validLoginId.duplicateCheckLoginId(signDto.nickName())
                && validPassword.duplicateCheckPassword(signDto.password(),signDto.checkPassword());
    }
}
