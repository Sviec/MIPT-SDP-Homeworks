package com.example.currencyprovider.filter;

import com.example.currencyprovider.service.MetricService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Component
public class LoggingFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);

    @Autowired
    private MetricService metricService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(req, 4096);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(res);

        long startTime = System.currentTimeMillis();

        try {
            chain.doFilter(requestWrapper, responseWrapper);
        } finally {
            long duration = System.currentTimeMillis() - startTime;

            log.info("REQUEST: {} {} from {} - duration: {}ms",
                    req.getMethod(),
                    req.getRequestURI(),
                    req.getRemoteAddr(),
                    duration);

            log.info("RESPONSE: {} - status: {}",
                    req.getRequestURI(),
                    responseWrapper.getStatus());

            metricService.recordRequest(req.getRemoteAddr(), duration, responseWrapper.getStatus() >= 500);
            responseWrapper.copyBodyToResponse();
        }
    }
}