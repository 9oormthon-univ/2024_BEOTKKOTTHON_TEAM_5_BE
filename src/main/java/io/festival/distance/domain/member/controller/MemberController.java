package io.festival.distance.domain.member.controller;

import io.festival.distance.domain.member.dto.AccountRequestDto;
import io.festival.distance.domain.member.dto.AccountResponseDto;
import io.festival.distance.domain.member.dto.MemberInfoDto;
import io.festival.distance.domain.member.dto.MemberSignDto;
import io.festival.distance.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/member")
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
    @DeleteMapping
    public ResponseEntity<String> delete(Principal principal){
        return ResponseEntity.ok(memberService.withDrawal(principal.getName()));
    }

    /** NOTE
     * 멤버 프로필 등록
     */
    @PostMapping("/info")
    public ResponseEntity<Long> createMemberProfile(Principal principal, @RequestBody MemberInfoDto memberInfoDto){
        return ResponseEntity.ok(memberService.updateMemberInfo(principal.getName(),memberInfoDto));
    }

    /** NOTE
     * 멤버 계정 조회
     */
    @GetMapping("/account")
    public ResponseEntity<AccountResponseDto> showAccount(Principal principal){
        return ResponseEntity.ok(memberService.memberAccount(principal.getName()));
    }

    /** NOTE
     * 멤버 계정 수정
     */
    @PatchMapping("/account/update")
    public ResponseEntity<Long> updateAccount(Principal principal,@RequestBody AccountRequestDto accountRequestDto){
        return ResponseEntity.ok(memberService.modifyAccount(principal.getName(),accountRequestDto));
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
    @PatchMapping("/profile/update")
    public ResponseEntity<Long> updateProfile(Principal principal,@RequestBody MemberInfoDto memberInfoDto){
        return ResponseEntity.ok(memberService.modifyProfile(principal.getName(),memberInfoDto));
    }

    /** NOTE
     * 멤버 ID값 반환
     */
    @GetMapping("/id")
    public ResponseEntity<Long> sendMemberId(Principal principal){
        return ResponseEntity.ok(memberService.findByLoginId(principal.getName()).getMemberId());
    }

    /** NOTE
     * 멤버의 프로필이 등록되어 있으면 true, 등록되어 있지 않으면 false반환
     */
    @GetMapping("/check/profile")
    public ResponseEntity<Boolean> checkProfile(Principal principal){
        return ResponseEntity.ok(memberService.isExistProfile(principal));
    }
}
