package com.internship.tool.controller;

import com.internship.tool.dto.ApiResponse;
import com.internship.tool.dto.LoginRequest;
import com.internship.tool.dto.RegisterRequest;
import com.internship.tool.dto.UserDto;
import com.internship.tool.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDto>> register(@Valid @RequestBody RegisterRequest request) {
        UserDto user = userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<UserDto>builder()
                .success(true)
                .message("User registered successfully")
                .data(user)
                .build());
    }

    // Login and refresh endpoints will be implemented in Day 5 (JWT)
}
