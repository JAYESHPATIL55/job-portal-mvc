package com.jayesh.jobportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class JobportalApplication {

	public static void main(String[] args) {
		System.setProperty("user.timezone", "UTC");
		SpringApplication.run(JobportalApplication.class, args);
	}

}
