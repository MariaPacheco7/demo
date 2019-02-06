package com.globomantics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.globomantics"})
public class ExampleserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExampleserviceApplication.class, args);
	}

}

