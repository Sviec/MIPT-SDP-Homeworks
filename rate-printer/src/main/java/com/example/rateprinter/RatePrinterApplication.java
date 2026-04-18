package com.example.rateprinter;

import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableDiscoveryClient
public class RatePrinterApplication {
	private static final Logger log = LoggerFactory.getLogger(RatePrinterApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RatePrinterApplication.class, args);

		String version = RatePrinterApplication.class.getPackage().getImplementationVersion();
		if (version == null) {
			version = "0.0.1-SNAPSHOT (dev)";
		}
		log.info("Application started - version: {}", version);
	}
	@PreDestroy
	public void onShutdown() {
		log.info("Performing cleanup before shutdown");
	}
}
