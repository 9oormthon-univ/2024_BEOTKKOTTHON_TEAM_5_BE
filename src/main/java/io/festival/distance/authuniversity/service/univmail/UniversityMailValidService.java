package io.festival.distance.authuniversity.service.univmail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniversityMailValidService {
    private final ValidMailLength validMailLength;
    private final ValidSpecialWord validSpecialWord;
    private final ValidSchoolEmail validSchoolEmail;
    public boolean checkMail(String email){
        String[] subName=email.split("@");
        if(!validMailLength.checkLength(subName[0]))
            throw new IllegalStateException("이메일 길이가 너무 짧습니다!");
        if(validSpecialWord.checkContainSpecialWord(subName[0]))
            throw new IllegalStateException("특수문자는 포함할 수 없습니다!");
        if(validSchoolEmail.duplicateCheckSchoolEmail(email))
            throw new IllegalStateException("이미 등록된 이메일입니다!");
        return true;
    }
}
