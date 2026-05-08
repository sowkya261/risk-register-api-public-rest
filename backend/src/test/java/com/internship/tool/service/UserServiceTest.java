package com.internship.tool.service;

import com.internship.tool.dto.RegisterRequest;
import com.internship.tool.dto.UserDto;
import com.internship.tool.entity.Role;
import com.internship.tool.entity.User;
import com.internship.tool.exception.BadRequestException;
import com.internship.tool.exception.ResourceNotFoundException;
import com.internship.tool.repository.RoleRepository;
import com.internship.tool.repository.UserRepository;
import com.internship.tool.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private EmailService emailService;
    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_EmailExists() {
        RegisterRequest req = RegisterRequest.builder().email("test@test.com").fullName("Test").password("pass").build();
        when(userRepository.existsByEmail("test@test.com")).thenReturn(true);
        assertThrows(BadRequestException.class, () -> userService.registerUser(req));
    }

    @Test
    void testRegisterUser_Success() {
        RegisterRequest req = RegisterRequest.builder().email("test@test.com").fullName("Test").password("pass").build();
        Role role = Role.builder().id(1L).name("USER").build();
        when(userRepository.existsByEmail("test@test.com")).thenReturn(false);
        when(roleRepository.findByName("USER")).thenReturn(Optional.of(role));
        when(passwordEncoder.encode(any())).thenReturn("hashed");
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));
        UserDto dto = userService.registerUser(req);
        assertEquals("test@test.com", dto.getEmail());
        assertEquals("Test", dto.getFullName());
        assertTrue(dto.getRoles().contains("USER"));
    }

    @Test
    void testGetUserByEmail_NotFound() {
        when(userRepository.findByEmail("notfound@test.com")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserByEmail("notfound@test.com"));
    }
}
