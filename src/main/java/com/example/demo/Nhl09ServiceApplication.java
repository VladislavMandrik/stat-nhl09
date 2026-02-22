package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class Nhl09ServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(Nhl09ServiceApplication.class, args);
	}
}
