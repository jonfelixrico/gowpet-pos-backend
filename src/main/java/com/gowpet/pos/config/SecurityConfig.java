package com.gowpet.pos.config;

import org.springframework.context.annotation.Bean; 
import org.springframework.context.annotation.Configuration; 
import org.springframework.security.authentication.AuthenticationManager; 
import org.springframework.security.authentication.AuthenticationProvider; 
import org.springframework.security.authentication.dao.DaoAuthenticationProvider; 
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration; 
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; 
import org.springframework.security.config.annotation.web.builders.HttpSecurity; 
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; 
import org.springframework.security.config.http.SessionCreationPolicy; 
import org.springframework.security.core.userdetails.UserDetailsService; 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; 
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.security.web.SecurityFilterChain; 
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.gowpet.pos.security.JwtAuthFilter; 

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	private JwtAuthFilter authFilter;
	private UserDetailsService userDetailsService;
	
	// Configuring HttpSecurity 
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { 
        return http
                .authorizeHttpRequests(authz -> authz
                		.requestMatchers("/auth/welcome", "/auth/addNewUser", "/auth/generateToken").permitAll()
                        .requestMatchers("/**").authenticated())
                .sessionManagement(smc -> smc.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider()) 
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class) 
                .build(); 
    } 
  
    // Password Encoding 
    @Bean
    PasswordEncoder passwordEncoder() { 
        return new BCryptPasswordEncoder(); 
    } 
  
    @Bean
    AuthenticationProvider authenticationProvider() { 
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(); 
        authenticationProvider.setUserDetailsService(userDetailsService); 
        authenticationProvider.setPasswordEncoder(passwordEncoder()); 
        return authenticationProvider; 
    } 
  
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception { 
        return config.getAuthenticationManager(); 
    } 
}
