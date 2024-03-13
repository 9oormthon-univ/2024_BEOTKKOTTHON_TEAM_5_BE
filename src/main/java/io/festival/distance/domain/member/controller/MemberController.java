package io.festival.distance.domain.member.controller;

import io.festival.distance.domain.member.dto.MemberInfoDto;
import io.festival.distance.domain.member.dto.MemberSignDto;
import io.festival.distance.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/com/member")
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
}
