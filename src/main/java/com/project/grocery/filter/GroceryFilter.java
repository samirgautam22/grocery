package com.project.grocery.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import com.project.grocery.service.LoginService;
import com.project.grocery.util.Constant;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 28, 2018
 * 
 */
@Component
@Order(Ordered .HIGHEST_PRECEDENCE)
public class GroceryFilter implements Filter {

	private static final Logger LOG = LoggerFactory.getLogger(GroceryFilter.class);
	
	@Value("${grocery.token.check}")
	private boolean check = false;
	
	@Autowired
	private LoginService loginService;
	
	
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		Enumeration<String> headerNames = request.getHeaderNames();

		Map<String, String> requestHeaders = new HashMap<String, String>();

		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = ((HttpServletRequest) req).getHeader(key);
			requestHeaders.put(key, value);
		}
		String originHeaders = requestHeaders.get("Origin");
		response.setHeader("Access-Control-Allow-Origin", originHeaders);
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers",
				"Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization,token,loginId");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Max-Age", "86400");
		String loginIdString = request.getHeader("loginId");
		System.out.println(loginIdString);
		if (loginIdString == null || loginIdString.isEmpty())
			loginIdString = request.getHeader("loginId");
		String token = request.getHeader("token");
		System.out.println(loginIdString);
		Long currentloginId = ((null == loginIdString || loginIdString.isEmpty())) ? 0L : Long.valueOf(loginIdString);
		String url = ((HttpServletRequest) req).getRequestURL().toString();
		LOG.debug("Accepted request: {}", url);
		System.out.println("Accepted request from}" + url);
		if (check) {
			LOG.debug("Validating token.");
			System.out.println("here1");
			if (url.contains(Constant.SWAGGER_URL)) {
				System.out.println("here2");
				if (!url.contains(Constant.LOGIN_API_V1) &&
						!url.contains(Constant.LOGOUT_API_V1)&&!url.contains(Constant.USER_API_V1)
						&& !url.contains(Constant.STATUS_API) && 
						!loginService.isValidToken(currentloginId, token)) {
					System.out.println("here3");
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					PrintWriter out = response.getWriter();
					out.print("{\"message\": \"" + Constant.TOKEN_INVALID_OR_EXPIRED + "\"}");
					out.flush();
					LOG.debug("You are not Authorized");
					return;
					
				}
			}
		}
		chain.doFilter(req, response);
	}
		
	@Override
	public void destroy() {
	}

}
