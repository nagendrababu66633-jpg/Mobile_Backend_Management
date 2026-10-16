package org.example.orderservice.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

@Component
public class CorrelationIdFilter implements Filter {

    private static final String CORRELATION_ID = "X-Correlation-ID";

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        long startTime = System.currentTimeMillis();

        HttpServletRequest httpRequest =
                (HttpServletRequest) request;

        HttpServletResponse httpResponse =
                (HttpServletResponse) response;

        String correlationId =
                httpRequest.getHeader(CORRELATION_ID);

        if (correlationId == null || correlationId.isBlank()) {
            correlationId = UUID.randomUUID().toString();
        }

        httpResponse.setHeader(CORRELATION_ID, correlationId);

        try {

            chain.doFilter(request, response);

        } finally {

            long executionTime =
                    System.currentTimeMillis() - startTime;

            System.out.println(
                    "{"
                            + "\"timestamp\":\"" + Instant.now() + "\","
                            + "\"service\":\"order-service\","
                            + "\"correlationId\":\"" + correlationId + "\","
                            + "\"endpoint\":\"" + httpRequest.getRequestURI() + "\","
                            + "\"method\":\"" + httpRequest.getMethod() + "\","
                            + "\"status\":" + httpResponse.getStatus() + ","
                            + "\"executionTimeMs\":" + executionTime
                            + "}"
            );
        }
    }
}