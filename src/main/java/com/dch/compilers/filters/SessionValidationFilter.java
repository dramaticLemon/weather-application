package com.dch.compilers.filters;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dch.compilers.services.SessionService;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component("sessionValidationFilter")
public class SessionValidationFilter implements Filter{
	private static final Logger log = LoggerFactory.getLogger(SessionValidationFilter.class);
	private final SessionService sessionService;

	@Autowired
	public SessionValidationFilter(SessionService sessionService) {
		this.sessionService = sessionService;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletResponse resp = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;
		
		String path = req.getRequestURI();
		log.debug("Processing request for path: {}", path);
		if (path.startsWith("/auth/sign-in") || path.startsWith("/auth/sign-up")) {
			log.debug("Skipping session validation for auth path: {}", path);
			chain.doFilter(request, response);
			return;
		}
		String sessionId = getSessionId(req);
		log.debug("Session ID from cookie: {}", sessionId);
		if (sessionId == null || !sessionService.isSessionValid(sessionId)) {
			log.warn("Invalid or missing session ID. Redirecting to sign-in. Session ID: {}", sessionId);
			resp.sendRedirect(
				"/auth/sign-in"
			);
			
			return;
		}
		log.debug("Session is valid. Continuing request for path: {}", path);
		chain.doFilter(request, response);
	}
	
	private String getSessionId(HttpServletRequest request) {
		if (request.getCookies() == null) return null;

		for (Cookie cookie :request.getCookies()) {
			if ("SESSION_ID".equals(cookie.getName())) {
				return cookie.getValue();
			}
		}
		return null;
	}

	 @Override
    public void destroy() {
        // ...
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // ... 
	}
}
