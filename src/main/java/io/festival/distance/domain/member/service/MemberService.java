package io.festival.distance.domain.member.service;

import io.festival.distance.domain.member.dto.*;
import io.festival.distance.domain.member.entity.Authority;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.repository.MemberRepository;
import io.festival.distance.domain.member.validsignup.ValidLoginId;
import io.festival.distance.domain.member.validsignup.ValidSignup;
import io.festival.distance.domain.memberhobby.service.MemberHobbyService;
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
    private final ValidLoginId validLoginId;
    private final MemberTagService memberTagService;
    private final MemberHobbyService memberHobbyService;
    private static final String PREFIX="#";
    /** NOTE
     * 회원가입
     * 중복된 이메일인지 확인
     * 중복된 아이디인지 확인
     */
    /** TODO
     * signdto -> signUpDto로 네이밍 변경
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
    public String withDrawal(String loginId) {
        memberRepository.deleteByLoginId(loginId);
        return loginId;
    }

    /** NOTE
     * Member Table에서 mbti, 멤버 캐릭터 수정
     * MemberTag Table에서 사용자가 고른 Tag 등록
     * MemberHobby Table에서 사용자가 고른 Hobby등록
     */
    @Transactional
    public Long updateMemberInfo(String loginId, MemberInfoDto memberInfoDto) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new DistanceException(ErrorCode.NOT_EXIST_MEMBER));
        member.memberInfoUpdate(memberInfoDto);
        List<MemberTagDto> tagList = memberInfoDto.memberTagDto()
                .stream()
                .toList();
        List<MemberHobbyDto> hobbyList = memberInfoDto.memberHobbyDto()
                .stream().
                toList();
        memberTagService.updateTag(member,tagList); //태그 저장
        memberHobbyService.updateHobby(member,hobbyList); //취미 저장
        return member.getMemberId();
    }

    public Member findMember(Long memberId){
        return memberRepository.findById(memberId)
                .orElseThrow(()-> new DistanceException(ErrorCode.NOT_EXIST_MEMBER));
    }

    public Member findByLoginId(String loginId){
        return memberRepository.findByLoginId(loginId)
                .orElseThrow(()-> new DistanceException(ErrorCode.NOT_EXIST_MEMBER1));
    }

    @Transactional(readOnly = true)
    public AccountResponseDto memberAccount(String loginId) {
        Member member = findByLoginId(loginId);
        return AccountResponseDto.builder()
                .loginId(member.getLoginId())
                .password(member.getPassword())
                .gender(member.getGender())
                .telNum(member.getTelNum())
                .build();
    }

    @Transactional
    public Long modifyAccount(String loginId, AccountRequestDto accountRequestDto) {
        Member member = findByLoginId(loginId);
        if(!validLoginId.duplicateCheckLoginId(accountRequestDto.loginId()))
            throw new IllegalStateException("입력이 유효하지 않습니다!");
        String encryptedPassword=encoder.encode(accountRequestDto.password());
        member.memberAccountModify(accountRequestDto,encryptedPassword);
        return member.getMemberId();
    }

    @Transactional(readOnly = true)
    public MemberInfoDto memberProfile(Long memberId) { //멤버 프로필 조회
        Member member = findMember(memberId);
        List<MemberHobbyDto> hobbyDtoList = memberHobbyService.showHobby(member);
        List<MemberTagDto> tagDtoList = memberTagService.showTag(member);
        return MemberInfoDto.builder()
                .memberCharacter(member.getMemberCharacter())
                .mbti(member.getMbti())
                .memberTagDto(tagDtoList)
                .memberHobbyDto(hobbyDtoList)
                .build();
    }

    public Long modifyProfile(String loginId, MemberInfoDto memberInfoDto) { // 사용자가 입력한 값이 들어있음
        Member member = findByLoginId(loginId);
        member.memberInfoUpdate(memberInfoDto); //mbti랑 멤버 캐릭터 이미지 수정
        memberTagService.modifyTag(member,memberInfoDto.memberTagDto());
        memberHobbyService.modifyHobby(member,memberInfoDto.memberHobbyDto());
        return member.getMemberId();
    }
}
