package de.demo.plangenerator.commons;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

/**
 * Servlet filter that initiates and manages MDC value that acts as correlation ID for the logs.
 */
@Component
public class LogCorrelationFilter implements Filter {

    public static final String CORRELATION_ID = "correlationID";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest) {
            UUID correlationId = UUID.randomUUID();
            MDC.put(CORRELATION_ID, correlationId.toString());
        }

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MDC.remove(CORRELATION_ID);
        }
    }
}
