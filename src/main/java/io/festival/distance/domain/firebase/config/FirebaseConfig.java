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

@RestController
@RequestMapping("/firebase")
@RequiredArgsConstructor //gpsService 생성자 자동 생성
@CrossOrigin //모든 외부 도메인의 요청을 허용한다.
@Configuration
public class FirebaseConfig {
	/** NOTE
	 * GoogleCredentials 객체를 생성 (Firebase에 접근시 서버 인증 정보)
 	 */
	// Firebase Admin SDK private key 파일 경로
	static final String FILEPATH = "src/main/java/io/festival/distance/domain/firebase/config/firebaseAdminSDKPrivateKey.json";
	@PostConstruct
	public void initialize() {
		try {
			String fullPath = System.getProperty("user.dir") + "/" + FILEPATH;
			System.out.println(fullPath);
			// FirebaseOptions 객체를 생성 (Firebase에 접근시 서버 인증 정보)
			FirebaseOptions options = FirebaseOptions.builder()
				.setCredentials(GoogleCredentials.fromStream(new FileInputStream(fullPath)))
				.build();
			// FirebaseApp 객체가 비어있다면 option 인자에 따라 다시 초기화
			if (FirebaseApp.getApps().isEmpty()) {
				System.out.println("FirebaseApp 비어있어요");
				FirebaseApp.initializeApp(options);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
