package com.gowpet.pos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class WebSecurityConfig {
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
     
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
     
    @SuppressWarnings("deprecation")
	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	// TODO do something about these deprecation notices
    	http.csrf().disable();
    	http.httpBasic().disable();
        http.authorizeRequests()
        	.requestMatchers(HttpMethod.POST, "/login").permitAll()
        	// TODO remove this on prod
        	.requestMatchers(HttpMethod.POST, "/user").permitAll()
        	.anyRequest().authenticated();
        http.headers().frameOptions().disable();
        return http.build();
    }
}
