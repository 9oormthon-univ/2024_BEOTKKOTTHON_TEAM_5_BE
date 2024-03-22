package io.festival.distance.domain.gps.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.festival.distance.domain.firebase.service.FCMService;
import io.festival.distance.domain.gps.dto.GpsDto;
import io.festival.distance.domain.gps.dto.GpsResponseDto;
import io.festival.distance.domain.gps.dto.MatchResponseDto;
import io.festival.distance.domain.gps.dto.MemberIdPairDto;
import io.festival.distance.domain.gps.service.GpsService;
import lombok.RequiredArgsConstructor;
import io.festival.distance.domain.firebase.dto.notificationDto;

import java.lang.reflect.ParameterizedType;
import java.security.Principal;

@RestController
@RequestMapping("/api/gps")
@RequiredArgsConstructor //gpsService 생성자 자동 생성
@CrossOrigin //모든 외부 도메인의 요청을 허용한다.
public class GpsController {
	private final GpsService gpsService;

	/** NOTE
	 * 유저 현재 위치 정보 갱신 API
	 */
	@PostMapping("/update")
	public ResponseEntity<GpsResponseDto> updateGps(Principal principal, @RequestBody GpsDto gpsDto) {
		return ResponseEntity.ok(gpsService.updateMemberGps(principal.getName(), gpsDto));
	}
	/** NOTE
	 * 현재 유저 위치의 반경에 다른 사용자들의 위치가 속하는지 판단 API
	 */
	@GetMapping(value = "/matching", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MatchResponseDto> matching(Principal principal) {
		return ResponseEntity.ok(gpsService.matchUser(principal.getName()));
	}

	@GetMapping(value = "/distance")
	public ResponseEntity<Double> distance(@RequestBody MemberIdPairDto memberIdPairDto) {
		return ResponseEntity.ok(gpsService.getDistance(memberIdPairDto));
	}
}
