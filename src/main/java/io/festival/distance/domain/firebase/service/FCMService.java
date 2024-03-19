package io.festival.distance.domain.firebase.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FCMService {
	public static void sendNotification(String clientToken) {

		String title = "서버 측 제목입니다";
		String message = "서버 측 메세지입니다";
		System.out.println("토큰: " + clientToken);
		// 알림 내용
		Message firebaseMessage = Message.builder()
			.setToken(clientToken)
			.setNotification( Notification.builder()
				.setTitle(title)
				.setBody(message)
				.build())
			.build();
		// 알림 전송
		try {
			String response = FirebaseMessaging.getInstance().send(firebaseMessage);
			System.out.println("보낸 메세지 내용?: " + response);
		} catch (FirebaseMessagingException e) {
			e.printStackTrace();
		}
	}

}
