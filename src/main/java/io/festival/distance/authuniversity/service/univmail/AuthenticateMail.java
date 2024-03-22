package io.festival.distance.authuniversity.service.univmail;

import io.festival.distance.authuniversity.config.mail.SendMailService;
import io.festival.distance.authuniversity.config.mail.dto.UnivMailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;


@Component
@RequiredArgsConstructor
public class AuthenticateMail {
    private final UniversityMailValidService mailValidService;
    private final SendMailService sendMailService;
    private String certificationNumber;

    public String sendNumber(String schoolEmail) throws MessagingException { //실 서비스
        //schoolEmail= MessageFormat.format("{0}@{1}.ac.kr", schoolEmail,getDomainByName(schoolEmail));
        UnivMailDto univMailDto = sendMailService.createCertificationNumber(schoolEmail);  //번호가 발급
        sendMailService.mailSend(univMailDto); //메일 전송
        //certificationNumber =sendMailService.getTempPassword();
        System.out.println(">>>>>>  "+univMailDto.getTempPw());
        return univMailDto.getTempPw();
    }

    public boolean checkCertificationNumber(String number,String num2){
        System.out.println("내가 입력한 번호>>"+ number);
        System.out.println("발송된 번호>> "+num2);
        return number.equals(num2);
    }
}
