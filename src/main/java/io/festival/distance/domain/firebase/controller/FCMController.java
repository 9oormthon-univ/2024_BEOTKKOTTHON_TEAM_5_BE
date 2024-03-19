package io.festival.distance.domain.firebase.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import io.festival.distance.domain.firebase.service.FCMService;

@RestController
@RequestMapping("/FCM")
@RequiredArgsConstructor //gpsService 생성자 자동 생성
@CrossOrigin //모든 외부 도메인의 요청을 허용한다.
public class FCMController {
	private final FCMService fcmService;
	@GetMapping("/send/{clientToken}")
	public ResponseEntity<String> sendFCM(@PathVariable String clientToken) {
		System.out.println("Controller 토큰 : " + clientToken);
		// String CT = "duhU6AsXPjf2-pf2AYziot:APA91bHPPY6OlPxf1IOrY_xFYU80KdG8MvirX0Z1oHRYq7rVB5cfgbiBPbzBbG7DvCsFf_TgiVuO2qacv_4tS7eZjA_qz_JtGfsQYs_lVIq9sPi6Y0ODAVpU2rgsZ5oSvozfPB2L7IX4";
		FCMService.sendNotification(clientToken);
		return ResponseEntity.ok("보내기 성공!");
	}
}
