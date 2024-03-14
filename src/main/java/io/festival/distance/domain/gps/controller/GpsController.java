package io.festival.distance.domain.gps.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.festival.distance.domain.gps.dto.GpsDto;
import io.festival.distance.domain.gps.dto.GpsResponseDto;
import io.festival.distance.domain.gps.service.GpsService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/com/gps")
@RequiredArgsConstructor //gpsService 생성자 자동 생성
@CrossOrigin //모든 외부 도메인의 요청을 허용한다.
public class GpsController {
	private final GpsService gpsService;

	/** NOTE
	 * 유저 현재 위치 정보 갱신 API
	 */
	@PostMapping("/update/{memberId}")
	public ResponseEntity<GpsResponseDto> updateGps(@PathVariable Long memberId, @RequestBody GpsDto gpsDto) {
		return ResponseEntity.ok(gpsService.updateGps(memberId, gpsDto));
	}
}
