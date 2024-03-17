package io.festival.distance.domain.member.controller;

import io.festival.distance.domain.member.dto.AccountRequestDto;
import io.festival.distance.domain.member.dto.AccountResponseDto;
import io.festival.distance.domain.member.dto.MemberInfoDto;
import io.festival.distance.domain.member.dto.MemberSignDto;
import io.festival.distance.domain.member.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.SpinnerUI;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@CrossOrigin
public class MemberController {
    private final MemberService memberService;

    /** NOTE
     * 회원가입 API
     */
    @PostMapping("/signup")
    public ResponseEntity<Long> signup(@RequestBody MemberSignDto signDto){
        return ResponseEntity.ok(memberService.createMember(signDto));
    }

    /** NOTE
     * 회원탈퇴 API
     */
    @DeleteMapping("/{memberId}")
    public ResponseEntity<Long> delete(@PathVariable Long memberId){
        return ResponseEntity.ok(memberService.withDrawal(memberId));
    }

    /** NOTE
     * 멤버 프로필 등록
     */
    @PostMapping("/info/{memberId}")
    public ResponseEntity<Long> createMemberProfile(@PathVariable Long memberId, @RequestBody MemberInfoDto memberInfoDto){
        return ResponseEntity.ok(memberService.updateMemberInfo(memberId,memberInfoDto));
    }

    /** NOTE
     * 멤버 계정 조회
     */
    @GetMapping("/account/{memberId}")
    public ResponseEntity<AccountResponseDto> showAccount(@PathVariable Long memberId){
        return ResponseEntity.ok(memberService.memberAccount(memberId));
    }

    /** NOTE
     * 멤버 계정 수정
     */
    @PatchMapping("/account/update/{memberId}")
    public ResponseEntity<Long> updateAccount(@PathVariable Long memberId,@RequestBody AccountRequestDto accountRequestDto){
        return ResponseEntity.ok(memberService.modifyAccount(memberId,accountRequestDto));
    }

    /** NOTE
     * 멤버 프로필 조회
     */
    @GetMapping("/profile/{memberId}")
    public ResponseEntity<MemberInfoDto> showProfile(@PathVariable Long memberId){
        return ResponseEntity.ok(memberService.memberProfile(memberId));
    }

    /** NOTE
     * 멤버 프로필 수정
     */
    @PatchMapping("/profile/update/{memberId}")
    public ResponseEntity<Long> updateProfile(@PathVariable Long memberId,@RequestBody MemberInfoDto memberInfoDto){
        return ResponseEntity.ok(memberService.modifyProfile(memberId,memberInfoDto));
    }
}
