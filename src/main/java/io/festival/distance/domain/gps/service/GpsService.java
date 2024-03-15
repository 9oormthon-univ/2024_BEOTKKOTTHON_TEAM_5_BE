package io.festival.distance.domain.gps.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.festival.distance.domain.gps.dto.GpsDto;
import io.festival.distance.domain.gps.dto.GpsResponseDto;
import io.festival.distance.domain.gps.dto.MatchResponseDto;
import io.festival.distance.domain.gps.dto.MatchUserDto;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.repository.MemberRepository;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GpsService {
	private final MemberRepository memberRepository;

	/** NOTE
	 * member 테이블의 longitude, latitude 갱신
	 */
	@Transactional
	public GpsResponseDto updateMemberGps(Long memberId, GpsDto gpsDto) {
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new DistanceException(ErrorCode.NOT_EXIST_MEMBER));
		member.memberGpsUpdate(gpsDto);
		return GpsResponseDto.builder()
				.memberId(member.getMemberId())
				.latitude(member.getLatitude())
				.longitude(member.getLongitude())
				.build();
	}
	/** NOTE
	 * member 테이블에서 특정 유저의 latitude, longitude 가져오기
	 // id, latitude, longitude 말고도 모든 data를 가져와야하나?
	 */
	@Transactional
	public MatchResponseDto matchUser(Long memberId) {
		final double searchRange = 200; // 200미터 이내 반경

		Member centerUser = memberRepository.findById(memberId)
			.orElseThrow(() -> new DistanceException(ErrorCode.NOT_EXIST_MEMBER));
		double centerLongitude = centerUser.getLongitude();
		double centerLatitude = centerUser.getLatitude();

		// 멤버를 필터링하고, 필터링된 결과를 List<Member>로 변환
		List<MatchUserDto> matchedUserList = memberRepository.findAll().stream()
			.filter(user -> {
				double userLongitude = user.getLongitude();
				double userLatitude = user.getLatitude();
				double distance = calculateDistance(centerLatitude, centerLongitude, userLatitude, userLongitude);
				System.out.println(user.getMemberId() + ": " + distance);
				return 0 < distance && distance <= searchRange; // 반경 내 user 필터링 (본인 제외)
			})
			.limit(4) // 최대 4명
			.map(user -> MatchUserDto.builder() //필요한 정보만 넘기기 위함
				.memberId(user.getMemberId())
				.mbti(user.getMbti())
				.nickName(user.getNickName())
				.department(user.getDepartment())
				.memberCharacter(user.getMemberCharacter())
				.build())
			.toList();
		return MatchResponseDto.builder()
			.matchedUsers(matchedUserList)
			.build();
	}
	/** NOTE
	 * 두 점 사이의 거리를 계산하는 메서드 (Haversine 공식 이용)
	 */
	private static double calculateDistance(double x1, double y1, double x2, double y2) {
		double distance; // 두 지점 사이 거리 (리턴값)
		double radius = 6371; // 지구 반지름(km)
		double toRadian = Math.PI / 180;

		double deltaLatitude = Math.abs(x1 - x2) * toRadian;
		double deltaLongitude = Math.abs(y1 - y2) * toRadian;

		double sinDeltaLat = Math.sin(deltaLatitude / 2);
		double sinDeltaLng = Math.sin(deltaLongitude / 2);
		double squareRoot = Math.sqrt(
			sinDeltaLat * sinDeltaLat +
				Math.cos(x1 * toRadian) * Math.cos(x2 * toRadian) * sinDeltaLng * sinDeltaLng);

		distance = 2 * radius * Math.asin(squareRoot);

		return distance;
	}

}
