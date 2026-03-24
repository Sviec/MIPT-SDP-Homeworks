package com.example.rateprinter.scheduler;

import com.example.rateprinter.client.CurrencyClient;
import com.example.rateprinter.model.CurrencyRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class RatePrintingScheduler {
    private final CurrencyClient currencyClient;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Autowired
    public RatePrintingScheduler(CurrencyClient currencyClient) {
        this.currencyClient = currencyClient;
    }

    @Scheduled(fixedDelay = 5000)
    public void printCurrencyRate() {
        String currentTime = LocalDateTime.now().format(formatter);
        CurrencyRate rate = currencyClient.getCurrentRate();

        if (rate != null) {
            System.out.println("("+currentTime+")"+rate);
        } else {
            System.err.println("("+currentTime+") the server is not responding");
        }
    }
}
