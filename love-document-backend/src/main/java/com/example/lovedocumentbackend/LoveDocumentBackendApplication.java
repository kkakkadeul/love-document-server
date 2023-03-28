package com.example.lovedocumentbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LoveDocumentBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoveDocumentBackendApplication.class, args);
	}

}
