package com.gowpet.pos.auth.service;

import io.jsonwebtoken.Claims; 
import io.jsonwebtoken.Jwts; 
import io.jsonwebtoken.SignatureAlgorithm; 
import io.jsonwebtoken.io.Decoders; 
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails; 
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function; 

/**
 * Reference used for the code: https://www.geeksforgeeks.org/spring-boot-3-0-jwt-authentication-with-spring-security-using-mysql-database/
 * @author Felix
 *
 */
@Service
public class JwtService {
	private String secret;
	private long duration;
	
	JwtService(
		@Value("${app.jwt.secret:ZWU0ZjA1N2YtODlkMS00YWFmLTg5MTQtOGNjMzNjN2RhZDA5}") String secret,
		// Default duration: 15 mins		
		@Value("${app.jwt.duration:900000}") long duration
	) {
		this.secret = secret;
		this.duration = duration;
	}

    public String generateToken(String userName) { 
        return createToken(Map.of(), userName); 
    } 
  
    private String createToken(Map<String, Object> claims, String userName) { 
        long currentMillis = System.currentTimeMillis();
    	return Jwts.builder() 
                .setClaims(claims) 
                .setSubject(userName) 
                .setIssuedAt(new Date(currentMillis)) 
                .setExpiration(new Date(currentMillis + duration)) 
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact(); 
    } 
  
    private Key getSignKey() { 
        byte[] keyBytes= Decoders.BASE64.decode(secret); 
        return Keys.hmacShaKeyFor(keyBytes); 
    } 
  
    public String extractUsername(String token) { 
        return extractClaim(token, Claims::getSubject); 
    } 
  
    public Date extractExpiration(String token) { 
        return extractClaim(token, Claims::getExpiration); 
    } 
  
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) { 
        final Claims claims = extractAllClaims(token); 
        return claimsResolver.apply(claims); 
    } 
  
    private Claims extractAllClaims(String token) { 
        return Jwts 
                .parserBuilder() 
                .setSigningKey(getSignKey()) 
                .build() 
                .parseClaimsJws(token) 
                .getBody(); 
    } 
  
    private Boolean isTokenExpired(String token) { 
        return extractExpiration(token).before(new Date()); 
    } 
  
    public Boolean validateToken(String token, UserDetails userDetails) { 
        final String username = extractUsername(token); 
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token)); 
    } 
}
