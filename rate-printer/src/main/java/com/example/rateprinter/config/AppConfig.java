package com.example.rateprinter.config;

import com.example.rateprinter.interceptor.LoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    @Autowired
    private LoggingInterceptor loggingInterceptor;

    @Bean
    @LoadBalanced
    @Primary
    public RestTemplate restTemplate() {
        RestTemplate template = new RestTemplate();
        template.getInterceptors().add(loggingInterceptor);
        return template;
    }
}
