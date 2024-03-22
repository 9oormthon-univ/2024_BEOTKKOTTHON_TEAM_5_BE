package io.festival.distance.authuniversity.controller;

import io.festival.distance.authuniversity.dto.CertificateDto;
import io.festival.distance.authuniversity.dto.EmailDto;
import io.festival.distance.authuniversity.dto.SchoolNameDto;
import io.festival.distance.authuniversity.service.univmail.AuthenticateMail;
import io.festival.distance.authuniversity.service.univmail.SendSchoolDomain;
import io.festival.distance.authuniversity.service.univmail.UniversityMailValidService;
import io.festival.distance.authuniversity.service.univmail.ValidSchoolEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/univ")
@CrossOrigin
public class MailController {
    private final SendSchoolDomain sendSchoolDomain;
    private final ValidSchoolEmail validationSchoolEmail;
    private final AuthenticateMail authenticateMail;
    private final UniversityMailValidService universityMailValidService;
    private String certificationNumber;
    /** NOTE
     * 학교 이름 선택시 해당 도메인 값 return
     */
    @PostMapping("/check/univ-domain")
    public ResponseEntity<String> checkDomain(@RequestBody SchoolNameDto schoolNameDto){
        return ResponseEntity.ok(sendSchoolDomain.sendDomain(schoolNameDto.schoolName()));
    }

    /** NOTE
     * 존재하는 이메일인지 중복 확인
     */
    @PostMapping("/check/email")
    public ResponseEntity<Boolean> checkSchoolEmail(@RequestBody EmailDto emailDto){
        return ResponseEntity.ok(universityMailValidService.checkMail(emailDto.schoolEmail()));
    }

    /** NOTE
     * 이메일 전송
     */
    @PostMapping("/send/email")
    public ResponseEntity<Void> sendEmail(@RequestBody EmailDto emailDto) throws MessagingException {
        certificationNumber = authenticateMail.sendNumber(emailDto.schoolEmail());
        return ResponseEntity.ok().build();
    }

    /** NOTE
     * 인증번호가 일치하는지 확인
     */
    @PostMapping("/certificate/email")
    public ResponseEntity<Boolean> certificationNumber(@RequestBody CertificateDto certificateDto){
        return ResponseEntity.ok(authenticateMail.checkCertificationNumber(certificateDto.number(),certificationNumber));
    }
}
