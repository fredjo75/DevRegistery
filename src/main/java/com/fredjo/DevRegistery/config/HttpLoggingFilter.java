package com.fredjo.DevRegistery.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

/**
 * HTTP Request/Response logging filter.
 * Logs all incoming HTTP requests and outgoing responses with execution time.
 */
@Component
public class HttpLoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(HttpLoggingFilter.class);
    private static final String REQUEST_ID_HEADER = "X-Request-Id";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Generate unique request ID
        String requestId = UUID.randomUUID().toString();
        long startTime = System.currentTimeMillis();

        try {
            // Log incoming request
            logger.info("[{}] {} {} - Remote IP: {}",
                requestId,
                request.getMethod(),
                request.getRequestURI(),
                getClientIpAddress(request));

            // Add request ID to response headers
            response.addHeader(REQUEST_ID_HEADER, requestId);

            // Continue with the request
            filterChain.doFilter(request, response);

        } finally {
            // Log response after request is processed
            long duration = System.currentTimeMillis() - startTime;
            logger.info("[{}] Response Status: {} - Duration: {}ms",
                requestId,
                response.getStatus(),
                duration);
        }
    }

    /**
     * Get client IP address from request.
     * Handles proxied requests (X-Forwarded-For header).
     *
     * @param request the HTTP request
     * @return the client IP address
     */
    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty()) {
            return xRealIp;
        }
        return request.getRemoteAddr();
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // Don't log health check endpoints
        String path = request.getRequestURI();
        return path.contains("/h2-console") || path.contains("/swagger-ui");
    }
}
