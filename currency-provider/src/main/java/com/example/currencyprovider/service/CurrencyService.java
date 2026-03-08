package com.example.currencyprovider.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class CurrencyService {
    private final Random random = new Random();
    private volatile BigDecimal currentRate;

    private static final double BASE_RATE = 90.0;
    private static final double MAX_CHANGE = 0.5;
    private static final double TREND_PROBABILITY = 0.7;
    private double lastChange = 0.0;

    @PostConstruct
    public void init() {
        this.currentRate = BigDecimal.valueOf(BASE_RATE)
                        .setScale(2, RoundingMode.HALF_UP);
        System.out.println("Start rate: " + currentRate);

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::updateRate, 1, 1, TimeUnit.SECONDS);
    }

    private void updateRate() {
        double change = generateNextChange();
        double newRate = currentRate.doubleValue() + change;

        this.currentRate = BigDecimal.valueOf(newRate)
                .setScale(2, RoundingMode.HALF_UP);
        this.lastChange = change;

        System.out.printf("Current exchange: %.2f (%+.2f)%n",currentRate, change);
    }

    private double generateNextChange() {
        boolean continueTrend = random.nextDouble() < TREND_PROBABILITY;
        double change;
        if (continueTrend && lastChange != 0) {
            double direction = lastChange > 0 ? 1 : -1;
            change = direction * random.nextDouble() * MAX_CHANGE;
        } else {
            change = (random.nextDouble() * 2 * MAX_CHANGE) - MAX_CHANGE;
        }
        return change;
    }

    public BigDecimal getCurrentCurrencyRate() {
        return currentRate;
    }
}
