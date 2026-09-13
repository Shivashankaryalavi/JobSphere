package com.project.JobPortal.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.JobPortal.dto.UserRequestDTO;
import com.project.JobPortal.dto.UserResponseDTO;
import com.project.JobPortal.service.UserService;

import jakarta.validation.Valid;
import com.project.JobPortal.dto.LoginRequestDTO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public UserResponseDTO registerUser(
            @RequestBody @Valid UserRequestDTO userDTO) {

        return userService.registerUser(userDTO);
    }
    
    @PostMapping("/login")
    public UserResponseDTO login(@RequestBody @Valid LoginRequestDTO loginDTO) {

        return userService.login(loginDTO);
    }
    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }
}