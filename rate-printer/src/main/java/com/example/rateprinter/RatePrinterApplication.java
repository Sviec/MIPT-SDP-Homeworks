package com.example.rateprinter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableDiscoveryClient
public class RatePrinterApplication {

	public static void main(String[] args) {
		SpringApplication.run(RatePrinterApplication.class, args);
	}

}
