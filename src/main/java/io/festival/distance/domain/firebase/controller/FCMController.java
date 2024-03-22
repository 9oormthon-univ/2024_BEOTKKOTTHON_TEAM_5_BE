package io.festival.distance.domain.firebase.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.festival.distance.domain.firebase.dto.notificationDto;
import lombok.RequiredArgsConstructor;
import io.festival.distance.domain.firebase.service.FCMService;

@RestController
@RequestMapping("/api/fcm")
@RequiredArgsConstructor //gpsService 생성자 자동 생성
@CrossOrigin //모든 외부 도메인의 요청을 허용한다.
public class FCMController {
	private final FCMService fcmService;
	/** NOTE
	 * FCM 푸시 알림 전송 API
	 */
	// @GetMapping(value = "/send/{clientToken}")
	// public ResponseEntity<notificationDto> sendFCM(@PathVariable String clientToken) {
	// 	notificationDto notification = FCMService.sendNotification(clientToken);
	// 	return ResponseEntity.ok(notification);
	// }
}
