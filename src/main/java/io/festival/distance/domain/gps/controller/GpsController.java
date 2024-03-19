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
import io.festival.distance.domain.gps.service.GpsService;
import lombok.RequiredArgsConstructor;
import io.festival.distance.domain.firebase.dto.notificationDto;

@RestController
@RequestMapping("/api/gps")
@RequiredArgsConstructor //gpsService 생성자 자동 생성
@CrossOrigin //모든 외부 도메인의 요청을 허용한다.
public class GpsController {
	private final GpsService gpsService;

	/** NOTE
	 * 유저 현재 위치 정보 갱신 API
	 */
	@PostMapping("/update/{memberId}")
	public ResponseEntity<GpsResponseDto> updateGps(@PathVariable Long memberId, @RequestBody GpsDto gpsDto) {
		return ResponseEntity.ok(gpsService.updateMemberGps(memberId, gpsDto));
	}
	/** NOTE
	 * 현재 유저 위치의 반경에 다른 사용자들의 위치가 속하는지 판단 API
	 */
	@GetMapping(value = "/matching/{memberId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MatchResponseDto> matching(@PathVariable Long memberId) {
		return ResponseEntity.ok(gpsService.matchUser(memberId));
	}
	@GetMapping(value = "/hell/{clientToken}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<notificationDto> matching2(@PathVariable String clientToken) {
		System.out.println("Controller 토큰 : " + clientToken);
		notificationDto notification = FCMService.sendNotification(clientToken);
		// notificationDto notification = null;
		return ResponseEntity.ok(notification);
	}
	// @GetMapping("/send")
	// public ResponseEntity<String> sendFCM() {
	// 	// String CT = "duhU6AsXPjf2-pf2AYziot:APA91bHPPY6OlPxf1IOrY_xFYU80KdG8MvirX0Z1oHRYq7rVB5cfgbiBPbzBbG7DvCsFf_TgiVuO2qacv_4tS7eZjA_qz_JtGfsQYs_lVIq9sPi6Y0ODAVpU2rgsZ5oSvozfPB2L7IX4";
	// }
}
