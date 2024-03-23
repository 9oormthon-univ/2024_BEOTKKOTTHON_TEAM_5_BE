package io.festival.distance.domain.gps.dto;

import io.festival.distance.domain.member.dto.MemberInfoDto;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.MemberService;
import lombok.Builder;

import java.util.List;

@Builder
public record MatchUserDto (long memberId,
                            String nickName,
                            String department,
                            MemberInfoDto memberInfoDto) {
	public static MatchUserDto fromMember(Member member, MemberService memberService) {
		// Member로부터 필요한 정보 추출 및 MemberInfoDto 생성 로직 추가
		long memberId = member.getMemberId();
		String nickName = member.getNickName();
		String department = member.getDepartment();
		// 예를 들어 memberService.memberProfile(memberId)로 MemberInfoDto를 생성
		MemberInfoDto memberInfoDto = memberService.memberProfile(memberId);

		// MatchUserDto 빌더를 사용하여 인스턴스 생성
		return MatchUserDto.builder()
			.memberId(memberId)
			.nickName(nickName)
			.department(department)
			.memberInfoDto(memberInfoDto)
			.build();
	}
}
