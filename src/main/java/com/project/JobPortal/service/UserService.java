package com.project.JobPortal.service;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.project.JobPortal.dto.UserRequestDTO;
import com.project.JobPortal.dto.UserResponseDTO;
import com.project.JobPortal.model.User;
import com.project.JobPortal.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.project.JobPortal.dto.LoginRequestDTO;
import com.project.JobPortal.security.JwtService;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;

    @Autowired
    public UserService(UserRepository userRepository , PasswordEncoder passwordEncoder,JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }
    

    public UserResponseDTO registerUser(UserRequestDTO userDTO) {
    	
    	if (userRepository.existsByEmail(userDTO.getEmail()))
    	{
    	    throw new IllegalArgumentException("Email already exists");
    	}

        User user = new User();

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole("CANDIDATE");

        User savedUser = userRepository.save(user);

        UserResponseDTO responseDTO = new UserResponseDTO();

        responseDTO.setId(savedUser.getId());
        responseDTO.setName(savedUser.getName());
        responseDTO.setEmail(savedUser.getEmail());
        responseDTO.setRole(savedUser.getRole());

        return responseDTO;
    }
    
    public UserResponseDTO login(LoginRequestDTO loginDTO) {

        Optional<User> user = userRepository.findByEmail(loginDTO.getEmail());

        if (user.isEmpty()) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        if (!passwordEncoder.matches(loginDTO.getPassword(),user.get().getPassword())) 
        {

            throw new IllegalArgumentException("Invalid email or password");
        }
        String token = jwtService.generateToken(user.get().getEmail());

        UserResponseDTO responseDTO = new UserResponseDTO();

        responseDTO.setId(user.get().getId());
        responseDTO.setName(user.get().getName());
        responseDTO.setEmail(user.get().getEmail());
        responseDTO.setRole(user.get().getRole());
        responseDTO.setToken(token);

        return responseDTO;
    }
    public void deleteUser(Integer id) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User loggedInUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (loggedInUser.getRole().equals("ADMIN")) {
            userRepository.deleteById(id);
            return;
        }

        if (!loggedInUser.getId().equals(id)) {
            throw new org.springframework.security.access.AccessDeniedException(
                    "You can delete only your own account"
            );
        }

        userRepository.deleteById(id);
    }
}