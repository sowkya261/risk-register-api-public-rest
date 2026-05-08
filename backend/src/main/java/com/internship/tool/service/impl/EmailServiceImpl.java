package com.internship.tool.service.impl;

import com.internship.tool.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;

    @Value("${MAIL_FROM}")
    private String from;

    @Async
    @Override
    public void sendRegistrationEmail(String to, String name) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject("Welcome to Tool-101!");
        message.setText("Hello " + name + ",\n\nThank you for registering at Tool-101.\n\nBest regards,\nTool-101 Team");
        mailSender.send(message);
    }
}
