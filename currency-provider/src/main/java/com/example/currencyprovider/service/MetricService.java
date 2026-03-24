package com.example.currencyprovider.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class MetricService {

    private final MeterRegistry meterRegistry;
    private final ConcurrentHashMap<String, Counter> requestCounters = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Timer> requestTimers = new ConcurrentHashMap<>();

    public MetricService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    public void recordRequest(String clientIp, long durationMs, boolean isError) {
        Counter counter = requestCounters.computeIfAbsent(clientIp,
                ip -> Counter.builder("http.server.requests.by.client")
                        .tag("client", ip)
                        .tag("error", String.valueOf(isError))
                        .description("Requests count by client IP")
                        .register(meterRegistry));
        counter.increment();

        Timer timer = requestTimers.computeIfAbsent(clientIp,
                ip -> Timer.builder("http.server.request.duration")
                        .tag("client", ip)
                        .description("Request duration by client")
                        .register(meterRegistry));
        timer.record(durationMs, TimeUnit.MILLISECONDS);
    }
}