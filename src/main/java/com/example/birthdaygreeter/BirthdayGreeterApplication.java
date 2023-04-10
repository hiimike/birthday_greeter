package com.example.birthdaygreeter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BirthdayGreeterApplication {

	public static void main(String[] args) {
		SpringApplication.run(BirthdayGreeterApplication.class, args);
	}

}
