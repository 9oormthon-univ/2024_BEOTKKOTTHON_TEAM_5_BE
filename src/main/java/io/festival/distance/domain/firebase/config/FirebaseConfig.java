package io.festival.distance.domain.firebase.config;
import java.io.FileInputStream;
import java.io.IOException;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Configuration
public class FirebaseConfig {
	/** NOTE
	 * GoogleCredentials 객체를 생성 (Firebase에 접근시 서버 인증 정보)
 	 */
	// Firebase Admin SDK private key 파일 경로
	static final String FILEPATH = "firebaseAdminSDKPrivateKey.json";;

	@Value("MY_JSON")
	private String myJson;

	@PostConstruct //spring 어플리케이션 실행될 때 아래 코드 바로 실행됨
	public void firebaseInitialize() {
		System.out.println("myJson: " + myJson);
		try {
			System.out.println("절대경로>>> "+ System.getProperty("user.dir"));
			//String fullPath = System.getProperty("user.dir") + "/" + FILEPATH;
			String fullPath="firebaseAdminSDKPrivateKey.json";
			System.out.println(">>>>>"+fullPath);
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