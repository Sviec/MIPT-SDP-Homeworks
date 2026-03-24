package com.example.rateprinter.client;

import com.example.rateprinter.model.CurrencyRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class CurrencyClient {
    private final RestTemplate restTemplate;

    private static final String SERVICE_URL = "http://currency-service/api/currency/usd-rub";

    @Autowired
    public CurrencyClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CurrencyRate getCurrentRate() {
        try {
            CurrencyRate rate = restTemplate.getForObject(SERVICE_URL, CurrencyRate.class);
            return rate;
        } catch (RestClientException e) {
            System.err.println("Error requesting exchange rate: " + e.getMessage());
            return null;
        }
    }
}
