package io.festival.distance.domain.member.valid;

import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidPassword {
    public boolean duplicateCheckPassword(String password,String checkPassword){
        if(!password.equals(checkPassword))
            throw new DistanceException(ErrorCode.NOT_CORRECT_PASSWORD);
        return true;
    }
}
