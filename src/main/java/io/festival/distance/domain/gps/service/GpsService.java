package io.festival.distance.domain.gps.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.festival.distance.domain.gps.dto.GpsDto;
import io.festival.distance.domain.gps.dto.GpsResponseDto;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.repository.MemberRepository;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GpsService {
	private final MemberRepository memberRepository;

	/** NOTE
	 * member 테이블의 longitude, latitude 갱신
	 */
	@Transactional
	public GpsResponseDto updateGps(Long memberId, GpsDto gpsDto) {
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new DistanceException(ErrorCode.NOT_EXIST_MEMBER));
		member.memberGpsUpdate(gpsDto);
		return new GpsResponseDto(member.getMemberId(), member.getLatitude(), member.getLongitude());
	}
}
