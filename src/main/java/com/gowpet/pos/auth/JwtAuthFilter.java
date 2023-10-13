package com.gowpet.pos.auth;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Base code code is from https://www.geeksforgeeks.org/spring-boot-3-0-jwt-authentication-with-spring-security-using-mysql-database/
 * @author Felix
 *
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
	private JwtService jwtService;
	private UserDetailsService userDetailsService;
		
	JwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService) {
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
	}

	@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException { 
        String authHeader = request.getHeader("Authorization"); 
        String token = null; 
        String username = null; 
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) { 
            token = authHeader.substring(7); 
            username = jwtService.extractUsername(token); 
        } 
  
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) { 
            UserDetails userDetails = userDetailsService.loadUserByUsername(username); 
            if (jwtService.validateToken(token, userDetails)) { 
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); 
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); 
                SecurityContextHolder.getContext().setAuthentication(authToken); 
            } 
        }

        filterChain.doFilter(request, response); 
    } 
}