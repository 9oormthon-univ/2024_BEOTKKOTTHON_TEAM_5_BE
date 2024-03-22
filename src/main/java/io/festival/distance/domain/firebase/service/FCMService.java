package io.festival.distance.domain.firebase.service;
import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import io.festival.distance.domain.firebase.dto.notificationDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FCMService {

	private final FirebaseMessaging firebaseMessaging;

	public notificationDto sendNotification(String clientToken) {
		String title = "서버 측 제목입니다";
		String message = "서버 측 메세지입니다";
		System.out.println("Client 토큰: " + clientToken);

		// 알림 내용
		Message firebaseMessage = Message.builder()
				.setToken(clientToken)
				.setNotification(Notification.builder()
						.setTitle(title)
						.setBody(message)
						.build())
				.build();

		// 알림 전송 및 결과 처리
		String response;
		try {
			response = firebaseMessaging.send(firebaseMessage);
		} catch (FirebaseMessagingException e) {
			e.printStackTrace();
			response = "알림 전송 실패";
		}
		return notificationDto.builder().FCMMessageID(response).build();
	}

}
