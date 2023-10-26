package com.gowpet.pos.auth.service;

import io.jsonwebtoken.Claims; 
import io.jsonwebtoken.Jwts; 
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails; 
import org.springframework.stereotype.Service;

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
	private long duration;
	private RsaService rsaSvc;
	
	JwtService(
		// Default duration: 15 mins		
		@Value("${app.jwt.duration:900000}") long duration,
		RsaService rsaSvc
	) {
		this.duration = duration;
		this.rsaSvc = rsaSvc;
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
                .signWith(rsaSvc.getPrivateKey(), SignatureAlgorithm.RS256).compact(); 
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
                .setSigningKey(rsaSvc.getPublicKey()) 
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
