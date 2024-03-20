package io.festival.distance.domain.firebase.config;

import java.io.FileInputStream;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import lombok.RequiredArgsConstructor;

public class FirebaseConfig {
	/** NOTE
	 * GoogleCredentials 객체를 생성 (Firebase에 접근시 서버 인증 정보)
 	 */
	// Firebase Admin SDK private key 파일 경로
	static final String FILEPATH = "src/main/java/io/festival/distance/domain/firebase/config/firebaseAdminSDKPrivateKey.json";
	@PostConstruct //spring 어플리케이션 실행될 때 아래 코드 바로 실행됨
	public void firebaseInitialize() {
		try {
			String fullPath = System.getProperty("user.dir") + "/" + FILEPATH;
			System.out.println(fullPath);
			// FirebaseOptions 객체를 생성 (Firebase 접근시 서버 인증 정보)
			FirebaseOptions firebaseOptions = FirebaseOptions.builder()
				.setCredentials(GoogleCredentials.fromStream(new FileInputStream(fullPath)))
				.build();

			FirebaseApp.initializeApp(firebaseOptions); // FirebaseApp 객체 초기화
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}