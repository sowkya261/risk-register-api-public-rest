package com.internship.tool.service;

import com.internship.tool.service.impl.EmailServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.internet.MimeMessage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmailServiceImplTest {

    @Test
    void sendRegistrationEmail_htmlRendered_sendsMimeMessage() throws Exception {
        JavaMailSender mailSender = mock(JavaMailSender.class);
        TemplateEngine templateEngine = mock(TemplateEngine.class);

        when(templateEngine.process(eq("emails/registration"), any(Context.class))).thenReturn("<p>Hi</p>");
        MimeMessage mime = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mime);

        EmailServiceImpl svc = new EmailServiceImpl(mailSender, templateEngine);
        // inject from via reflection or property — use default field set
        java.lang.reflect.Field f = EmailServiceImpl.class.getDeclaredField("from");
        f.setAccessible(true);
        f.set(svc, "noreply@tool101.test");

        svc.sendRegistrationEmail("user@test","Test User");

        // verify that createMimeMessage and send were called
        verify(mailSender, timeout(1000)).createMimeMessage();
        verify(mailSender, timeout(1000)).send(mime);
    }

    @Test
    void sendRegistrationEmail_templateFails_fallsBackToPlaintext() throws Exception {
        JavaMailSender mailSender = mock(JavaMailSender.class);
        TemplateEngine templateEngine = mock(TemplateEngine.class);

        when(templateEngine.process(eq("emails/registration"), any(Context.class))).thenThrow(new RuntimeException("render fail"));

        EmailServiceImpl svc = new EmailServiceImpl(mailSender, templateEngine);
        java.lang.reflect.Field f = EmailServiceImpl.class.getDeclaredField("from");
        f.setAccessible(true);
        f.set(svc, "noreply@tool101.test");

        svc.sendRegistrationEmail("user@test","Test User");

        // verify that send(SimpleMailMessage) was attempted on fallback
        ArgumentCaptor<org.springframework.mail.SimpleMailMessage> captor = ArgumentCaptor.forClass(org.springframework.mail.SimpleMailMessage.class);
        verify(mailSender, timeout(1000)).send(captor.capture());
        org.springframework.mail.SimpleMailMessage sent = captor.getValue();
        assertEquals("user@test", sent.getTo()[0]);
        assertTrue(sent.getText().contains("Thank you for registering"));
    }
}
