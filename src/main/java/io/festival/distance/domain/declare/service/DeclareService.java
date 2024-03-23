package io.festival.distance.domain.declare.service;

import io.festival.distance.domain.declare.dto.DeclareDto;
import io.festival.distance.domain.declare.entity.Declare;
import io.festival.distance.domain.declare.repository.DeclareRepository;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.MemberService;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class DeclareService {
    private final MemberService memberService;
    private final DeclareRepository declareRepository;
    @Transactional
    public void writeDeclare(DeclareDto declareDto, Principal principal) {
        Member me = memberService.findByLoginId(principal.getName()); //나 => 신고하는 사람
        Member opponent = memberService.findMember(declareDto.opponentId()); // 상대방 => 신고받는 사람
        if(declareRepository.existsByMeAndOpponent(me,opponent))
            throw new DistanceException(ErrorCode.EXIST_DECLARE);

        memberService.increaseDeclare(opponent.getMemberId()); //신고하기 갯수 증가
        Declare declare = Declare.builder()
                .declareContent(declareDto.declareContent())
                .me(me)
                .opponent(opponent)
                .build();
        declareRepository.save(declare);
        if(opponent.getDeclarationCount()==3)
            memberService.blockAccount(opponent.getMemberId());
    }
}
