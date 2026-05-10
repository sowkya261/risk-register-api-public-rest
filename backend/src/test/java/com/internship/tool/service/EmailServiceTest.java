package com.internship.tool.service;

import com.internship.tool.service.impl.EmailServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmailServiceTest {
    @Mock
    private JavaMailSender mailSender;
    @InjectMocks
    private EmailServiceImpl emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendRegistrationEmail() {
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));
        emailService.sendRegistrationEmail("test@demo.com", "Test User");
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}
