package com.internship.tool.controller;


import com.internship.tool.dto.*;
import com.internship.tool.security.JwtUtil;
import com.internship.tool.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDto>> register(@Valid @RequestBody RegisterRequest request) {
    UserDto user = userService.registerUser(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<UserDto>builder()
        .success(true)
        .message("User registered successfully")
        .data(user)
        .build());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtResponse>> login(@Valid @RequestBody LoginRequest request) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String token = jwtUtil.generateToken(userDetails.getUsername());
    UserDto user = userService.getUserByEmail(userDetails.getUsername());
    JwtResponse jwtResponse = JwtResponse.builder()
        .token(token)
        .email(user.getEmail())
        .fullName(user.getFullName())
        .build();
    return ResponseEntity.ok(ApiResponse.<JwtResponse>builder()
        .success(true)
        .message("Login successful")
        .data(jwtResponse)
        .build());
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<JwtResponse>> refresh(@RequestHeader("Authorization") String authHeader) {
    String token = authHeader != null && authHeader.startsWith("Bearer ") ? authHeader.substring(7) : null;
    if (token == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.<JwtResponse>builder()
            .success(false)
            .message("Missing or invalid Authorization header")
            .build());
    }
    String email = jwtUtil.extractUsername(token);
    String newToken = jwtUtil.generateToken(email);
    UserDto user = userService.getUserByEmail(email);
    JwtResponse jwtResponse = JwtResponse.builder()
        .token(newToken)
        .email(user.getEmail())
        .fullName(user.getFullName())
        .build();
    return ResponseEntity.ok(ApiResponse.<JwtResponse>builder()
        .success(true)
        .message("Token refreshed successfully")
        .data(jwtResponse)
        .build());
    }
}
