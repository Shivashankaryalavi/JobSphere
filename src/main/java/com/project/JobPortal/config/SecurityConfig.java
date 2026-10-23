package com.project.JobPortal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.project.JobPortal.security.JwtAuthenticationFilter;

import jakarta.servlet.DispatcherType;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {

    http
    .csrf(csrf -> csrf.disable())
    .cors(cors -> cors.configurationSource(request -> {
        var corsConfig = new org.springframework.web.cors.CorsConfiguration();

        corsConfig.setAllowedOrigins(
            java.util.List.of("http://localhost:5173")
        );

        corsConfig.setAllowedMethods(
            java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")
        );

        corsConfig.setAllowedHeaders(
            java.util.List.of("Authorization", "Content-Type")
        );

        return corsConfig;
    }))
        .authorizeHttpRequests(auth -> auth
        		.dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
        		.requestMatchers("/login", "/users", "/jobs").permitAll()
        	    .requestMatchers(
        	        org.springframework.http.HttpMethod.POST, "/jobs"
        	    ).hasAuthority("RECRUITER")
        	    
        	    .requestMatchers(
        	    	    org.springframework.http.HttpMethod.PUT, "/jobs/**"
        	    	).hasAuthority("RECRUITER")

        	    	.requestMatchers(
        	    	    org.springframework.http.HttpMethod.DELETE, "/jobs/**"
        	    	).hasAuthority("RECRUITER")
        	    	
        	    	.requestMatchers(
        	    		    org.springframework.http.HttpMethod.DELETE, "/users/**"
        	    		).authenticated()
        	    	
        	    .anyRequest().authenticated()
        	)
        .exceptionHandling(exception -> exception
        	    .authenticationEntryPoint(
        	        new org.springframework.security.web.authentication.HttpStatusEntryPoint(
        	            org.springframework.http.HttpStatus.UNAUTHORIZED
        	        )
        	    )
        	)
        .addFilterBefore(
            jwtAuthenticationFilter,
            org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class
        );

    return http.build();
}

    
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter)
    {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
}