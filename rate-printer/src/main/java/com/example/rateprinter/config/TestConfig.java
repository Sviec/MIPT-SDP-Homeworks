package com.example.rateprinter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TestConfig {

    @Bean(name = "testRestTemplate")
    public RestTemplate testRestTemplate() {
        return new RestTemplate();
    }
}