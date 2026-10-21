package com.project.JobPortal.security;

import java.io.IOException;

import com.project.JobPortal.repository.UserRepository;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;


import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import com.project.JobPortal.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter
{
	private UserRepository userRepository;
	
	private JwtService jwtService;
	
	public JwtAuthenticationFilter(UserRepository userRepository ,JwtService jwtService) {
	    this.userRepository = userRepository;
	    this.jwtService = jwtService;
	}

	@Override
	protected void doFilterInternal(
	        HttpServletRequest request,
	        HttpServletResponse response,
	        FilterChain filterChain)
	        throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");
		System.out.println("Authorization header present: " + (authHeader != null));
		
		
		if (authHeader != null && authHeader.startsWith("Bearer ")) {

		    String jwt = authHeader.substring(7);
		    
		    String email;

		    try 
		    {
		        email = jwtService.extractEmail(jwt);
		        System.out.println("JWT validated. Email: " + email);
		    } 
		    catch (JwtException e) {
		    	HttpServletResponse httpResponse = (HttpServletResponse) response;
		    	httpResponse.setContentType("application/json");
		    	httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		    	httpResponse.getWriter().write("{\"message\":\"Invalid or expired JWT\"}");
		        return;
		    }
		    
		    
		    Optional<User> user = userRepository.findByEmail(email);

		    if (user.isPresent() && SecurityContextHolder.getContext().getAuthentication() == null)
		    {
		    	String username = user.get().getEmail();
		    	String role = user.get().getRole();
		    	
		    	SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
		    	
		    	UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, java.util.Collections.singletonList(authority));
		    	SecurityContextHolder.getContext().setAuthentication(authentication);
		    	

		    }
		}
		filterChain.doFilter(request, response);
		
	}

}
