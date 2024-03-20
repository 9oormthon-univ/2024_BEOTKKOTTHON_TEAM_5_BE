package io.festival.distance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
@SpringBootApplication
@EnableJpaAuditing
public class DistanceApplication {
	public static void main(String[] args) {
		SpringApplication.run(DistanceApplication.class, args);
	}

}
