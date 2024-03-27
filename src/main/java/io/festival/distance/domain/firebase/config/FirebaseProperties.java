package io.festival.distance.domain.firebase.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class FirebaseProperties {

    private final String type;
    private final String project_id;
    private final String private_key_id;
    private final String private_key;
    private final String client_email;
    private final String client_id;
    private final String auth_uri;
    private final String token_uri;
    private final String auth_provider_x509_cert_url;
    private final String client_x509_cert_url;
    private final String universe_domain;

    public FirebaseProperties(
            @Value("${fcm.project-id}") String project_id,
            @Value("${fcm.private-key-id}") String private_key_id,
            @Value("${fcm.private-key}") String private_key,
            @Value("${fcm.client-email}") String client_email,
            @Value("${fcm.client-id}") String client_id
    ) {
        this.type = "service_account";
        this.project_id = project_id;
        this.private_key_id = private_key_id;
        this.private_key = private_key.replaceAll("\\\\n", "\n");
        this.client_email = client_email;
        this.client_id = client_id;
        this.auth_uri = "https://accounts.google.com/o/oauth2/auth";
        this.token_uri = "https://oauth2.googleapis.com/token";
        this.auth_provider_x509_cert_url = "https://www.googleapis.com/oauth2/v1/certs";
        this.client_x509_cert_url = "https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-rol2k%40distance-97455.iam.gserviceaccount.com";
        this.universe_domain = "googleapis.com";
    }

    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            // todo
            throw new IllegalArgumentException("안좋은 예시");
        }
    }
}
