package com.example.rateprinter.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component
public class LoggingInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger log = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        long startTime = System.currentTimeMillis();

        log.info("📤 OUTGOING REQUEST: {} {}", request.getMethod(), request.getURI());

        ClientHttpResponse response = execution.execute(request, body);

        long duration = System.currentTimeMillis() - startTime;

        // Копируем тело ответа в массив байтов
        byte[] responseBodyBytes = StreamUtils.copyToByteArray(response.getBody());
        String responseBody = new String(responseBodyBytes, StandardCharsets.UTF_8);

        log.info("📥 OUTGOING RESPONSE: {} - status: {} - duration: {}ms\nBody: {}",
                request.getURI(),
                response.getStatusCode(),
                duration,
                responseBody);

        // Возвращаем обертку, которая позволяет прочитать тело повторно
        return new BufferingClientHttpResponseWrapper(response, responseBodyBytes);
    }

    /**
     * Обертка для ClientHttpResponse, которая сохраняет тело ответа
     * и позволяет читать его повторно
     */
    private static class BufferingClientHttpResponseWrapper implements ClientHttpResponse {

        private final ClientHttpResponse original;
        private final byte[] bodyBytes;

        public BufferingClientHttpResponseWrapper(ClientHttpResponse original, byte[] bodyBytes) {
            this.original = original;
            this.bodyBytes = bodyBytes;
        }

        @Override
        public org.springframework.http.HttpStatusCode getStatusCode() throws IOException {
            return original.getStatusCode();
        }

        @Override
        public String getStatusText() throws IOException {
            return original.getStatusText();
        }

        @Override
        public void close() {
            original.close();
        }

        @Override
        public InputStream getBody() throws IOException {
            return new ByteArrayInputStream(bodyBytes);
        }

        @Override
        public org.springframework.http.HttpHeaders getHeaders() {
            return original.getHeaders();
        }
    }
}