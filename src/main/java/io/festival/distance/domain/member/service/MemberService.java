package io.festival.distance.domain.member.service;

import io.festival.distance.domain.member.dto.MemberInfoDto;
import io.festival.distance.domain.member.dto.MemberSignDto;
import io.festival.distance.domain.member.dto.MemberTagDto;
import io.festival.distance.domain.member.entity.Authority;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.repository.MemberRepository;
import io.festival.distance.domain.member.valid.ValidSignup;
import io.festival.distance.domain.membertag.service.MemberTagService;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final PasswordEncoder encoder;
    private final MemberRepository memberRepository;
    private final ValidSignup validSignup;
    private final MemberTagService memberTagService;
    private static final String PREFIX="#";
    /** NOTE
     * 회원가입
     * 중복된 이메일인지 확인
     * 중복된 아이디인지 확인
     */
    @Transactional
    public Long createMember(MemberSignDto signDto) {
        if(!validSignup.validationSignup(signDto))
            throw new IllegalStateException("입력이 유효하지 않습니다!");
        Member member = Member.builder()
                .schoolEmail(signDto.schoolEmail())
                .loginId(signDto.loginId())
                .password(encoder.encode(signDto.password()))
                .gender(signDto.gender())
                .nickName(signDto.nickName())
                .telNum(signDto.telNum())
                .school(signDto.school())
                .college(signDto.college())
                .department(signDto.department())
                .authority(Authority.ROLE_USER)
                .activated(true)
                .build();
       memberRepository.save(member);
       String nickName= signDto.nickName()+PREFIX+member.getMemberId();
       member.memberNicknameUpdate(nickName);
       return member.getMemberId();
    }

    /** NOTE
     * 회원 PK값으로 DB에서 삭제 -> 회원탈퇴
     */
    @Transactional
    public Long withDrawal(Long memberId) {
        memberRepository.deleteById(memberId);
        return memberId;
    }

    /** NOTE
     * Member Table에서 mbti, 멤버 캐릭터 수정
     * MemberTag Table에서 사용자가 고른 Tag 등록
     */
    @Transactional
    public Long updateMemberInfo(Long memberId, MemberInfoDto memberInfoDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new DistanceException(ErrorCode.NOT_EXIST_MEMBER));
        member.memberInfoUpdate(memberInfoDto);
        List<MemberTagDto> tagList = memberInfoDto.memberTagDto().stream()
                .toList();
        memberTagService.updateTag(member,tagList);
        return memberId;
    }
}
