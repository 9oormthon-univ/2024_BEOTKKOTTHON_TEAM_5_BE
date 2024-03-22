package io.festival.distance.authuniversity.service.univmail;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ValidSpecialWord {
    public boolean checkContainSpecialWord(String email){
        String specialWord="[^a-zA-Z0-9]";
        Pattern pattern=Pattern.compile(specialWord);
        Matcher matcher=pattern.matcher(email);
        return matcher.find(); //있으면 true, 없으면 false
    }
}
