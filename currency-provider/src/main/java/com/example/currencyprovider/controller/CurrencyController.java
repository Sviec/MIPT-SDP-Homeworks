package com.example.currencyprovider.controller;

import com.example.currencyprovider.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/currency")
public class CurrencyController {
    private final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("usd-rub")
    public Map<String, Object> getRate() {
        BigDecimal rate = currencyService.getCurrentCurrencyRate();
        Map<String, Object> response = new HashMap<>();

        response.put("currency", "USD-RUB");
        response.put("rate", rate);
        response.put("timestamp", System.currentTimeMillis());

        return response;
    }
}
