package com.example.veggietracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class VeggietrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(VeggietrackerApplication.class, args);
	}

}
