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
	public notificationDto sendNotification(String clientToken, String senderNickName, String chatMessage) {
		System.out.println("Client 토큰: " + clientToken);
		// 알림 내용
		Message firebaseMessage = createNotificationContent(senderNickName, clientToken, chatMessage);
		// 알림 전송
		String response;
		try {
			response = FirebaseMessaging.getInstance().send(firebaseMessage);
		} catch (FirebaseMessagingException e) {
			e.printStackTrace();
			response = "알림 전송 실패";
		}
		return notificationDto.builder().FCMMessageID(response).build();
	}

	// 다른 곳에서도 재사용 가능하도록 분리함!
	private Message createNotificationContent(String senderNickName, String receiverClientToken, String chatMessage) {
		// 알림 내용
		return Message.builder()
			.setToken(receiverClientToken)
			.setNotification(Notification.builder()
				.setTitle(senderNickName)
				.setBody(chatMessage)
				.build())
			.build();
	}
}
