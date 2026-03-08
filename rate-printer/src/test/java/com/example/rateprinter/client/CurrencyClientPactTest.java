package com.example.rateprinter.client;


import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.example.rateprinter.model.CurrencyRate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(PactConsumerTestExt.class)
@SpringBootTest
@PactTestFor(providerName = "currency-provider", port = "8081", pactVersion = PactSpecVersion.V3)
public class CurrencyClientPactTest {

    @Autowired
    @Qualifier("testRestTemplate")
    private RestTemplate restTemplate;

    @Pact(consumer = "rate-printer")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        return builder
                .given("provider is running")
                .uponReceiving("request for USD-RUB rate")
                .path("/api/currency/usd-rub")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(new PactDslJsonBody()
                        .stringType("currency", "USD-RUB")
                        .numberType("rate", 90.25)
                        .integerType("timestamp"))
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "createPact")
    void testGetCurrentRate() {
        // ИСПРАВЛЕНО: прямой вызов mock-сервера Pact, а не через клиент с service discovery
        String url = "http://localhost:8081/api/currency/usd-rub";
        CurrencyRate rate = restTemplate.getForObject(url, CurrencyRate.class);

        assertNotNull(rate);
        assertEquals("USD-RUB", rate.getCurrency());
        assertNotNull(rate.getRate());
        assertNotNull(rate.getTimestamp());
    }
}