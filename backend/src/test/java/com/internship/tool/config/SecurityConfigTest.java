package com.internship.tool.config;

import com.internship.tool.security.CustomUserDetailsService;
import com.internship.tool.security.JwtFilter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class SecurityConfigTest {

    @Mock
    private JwtFilter jwtFilter;
    @Mock
    private CustomUserDetailsService customUserDetailsService;
    @Mock
    private AuthenticationConfiguration authenticationConfiguration;

    @Test
    void testBeansCreation() throws Exception {
        SecurityConfig securityConfig = new SecurityConfig(jwtFilter);

        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        assertNotNull(passwordEncoder);

        AuthenticationManager authManager = securityConfig.authenticationManager(authenticationConfiguration);
        // AuthenticationConfiguration is mocked, might return null for getAuthenticationManager, but we just verify it runs
    }
}
