package io.festival.distance.authuniversity.config.mail;

import io.festival.distance.authuniversity.config.mail.dto.UnivMailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class SendMailService {
    private static final String FROM_ADDRESS = "seoulcafecheckin@gmail.com";
    private final JavaMailSender javaMailSender;

    public UnivMailDto createCertificationNumber(String userEmail) {
        String tempPassword = getTempPassword();
        return UnivMailDto.builder()
                .mailAddress(userEmail)
                .title(userEmail + "님의 이메일 인증번호 입니다.")
                .message("<h1>인증번호 발급</h1>" +
                        "<br/>" + userEmail + "님 " +
                        "<br/>인증번호 입니다." +
                        "<br/>인증번호 :   <h2>" + tempPassword + "</h2>")
                .build();
    }
    public String getTempPassword() {
        char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        StringBuilder str = new StringBuilder();

        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str.append(charSet[idx]);
        }
        return str.toString();
    }

    public void mailSend(UnivMailDto mailDto) throws MessagingException {
        System.out.println("이멜 전송 완료!");
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
        messageHelper.setTo(mailDto.getMailAddress());
        messageHelper.setFrom(SendMailService.FROM_ADDRESS);
        messageHelper.setSubject(mailDto.getTitle());
        messageHelper.setText(mailDto.getMessage(), true);
        javaMailSender.send(message);
    }
}
