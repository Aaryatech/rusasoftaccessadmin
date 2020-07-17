package com.ats.rusaaccessweb;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.filter.OncePerRequestFilter;

public class AddHeaderFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.err.println("In ");
		response.setHeader("X-XSS-Protection", "1; mode=block");
        response.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains"); 
        response.setHeader("X-Content-Type-Options", "nosniff"); 
        response.setHeader("Cache-control", "no-store, no-cache"); 
        response.setHeader("X-Frame-Options", "DENY"); 
        response.setHeader("Set-Cookie", "XSRF-TOKEN=NDKDdfdsfkldsfNd3SZAJfwLsTl5WUgOkE; Path=/; Secure;HttpOnly");
       
       
        filterChain.doFilter(request, response);
      // if(request.getPathInfo())
       // System.err.println("Req request.getQueryString() "+request.getQueryString());
       // System.err.println("request.getHeader(\"user-agent\"); " +request.getHeader("user-agent"));
	}

}
