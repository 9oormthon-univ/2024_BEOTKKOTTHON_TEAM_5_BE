package io.festival.distance.domain.firebase.config;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.annotation.PostConstruct;

import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Configuration
public class FirebaseConfig {
	/** NOTE
	 * GoogleCredentials 객체를 생성 (Firebase에 접근시 서버 인증 정보)
 	 */

	private final FirebaseProperties properties;

    public FirebaseConfig(FirebaseProperties properties) {
        this.properties = properties;
    }

	@Bean
	public FirebaseMessaging firebaseMessaging() {

		System.out.println(properties.toJson());
		try {
			// FirebaseOptions 객체를 생성 (Firebase 접근시 서버 인증 정보)
			FirebaseOptions firebaseOptions = FirebaseOptions.builder()
				.setCredentials(GoogleCredentials.fromStream(new ByteArrayInputStream(properties.toJson().getBytes())))
				.build();

			FirebaseApp.initializeApp(firebaseOptions); // FirebaseApp 객체 초기화
		} catch (IOException e) {
			e.printStackTrace();
		}

		return FirebaseMessaging.getInstance();
	}
}