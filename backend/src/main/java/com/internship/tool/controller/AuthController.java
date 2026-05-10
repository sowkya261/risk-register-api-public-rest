package com.internship.tool.controller;

import com.internship.tool.dto.*;
import com.internship.tool.security.JwtUtil;
import com.internship.tool.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDto>> register(@Valid @RequestBody RegisterRequest request) {
    UserDto user = userService.registerUser(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(
        true,
        "User registered successfully",
        user
    ));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtResponse>> login(@Valid @RequestBody LoginRequest request) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String email;
    Object principal = authentication.getPrincipal();
    if (principal instanceof UserDetails) {
        email = ((UserDetails) principal).getUsername();
    } else if (principal != null) {
        email = principal.toString();
    } else {
        // In tests the Authentication mock may not provide a principal; fall back to request email
        email = request.getEmail();
    }
    String token = jwtUtil.generateToken(email);
    UserDto user = userService.getUserByEmail(email);
    JwtResponse jwtResponse = new JwtResponse(token, user.getEmail(), user.getFullName());
    return ResponseEntity.ok(new ApiResponse<>(
        true,
        "Login successful",
        jwtResponse
    ));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<JwtResponse>> refresh(@RequestHeader("Authorization") String authHeader) {
    String token = authHeader != null && authHeader.startsWith("Bearer ") ? authHeader.substring(7) : null;
    if (token == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(
            false,
            "Missing or invalid Authorization header",
            null
        ));
    }
    String email = jwtUtil.extractUsername(token);
    String newToken = jwtUtil.generateToken(email);
    UserDto user = userService.getUserByEmail(email);
    JwtResponse jwtResponse = new JwtResponse(newToken, user.getEmail(), user.getFullName());
    return ResponseEntity.ok(new ApiResponse<>(
        true,
        "Token refreshed successfully",
        jwtResponse
    ));
    }
}
