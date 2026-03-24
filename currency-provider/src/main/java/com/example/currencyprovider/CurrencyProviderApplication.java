package com.example.currencyprovider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
public class CurrencyProviderApplication {
	private static final Logger log = LoggerFactory.getLogger(CurrencyProviderApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CurrencyProviderApplication.class, args);

		String version = CurrencyProviderApplication.class.getPackage().getImplementationVersion();
		if (version == null) {
			version = "0.0.1-SNAPSHOT (dev)";
		}
		log.info("Application started - version: {}", version);
	}


}
