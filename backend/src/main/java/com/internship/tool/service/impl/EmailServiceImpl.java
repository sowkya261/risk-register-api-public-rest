package com.internship.tool.service.impl;

import com.internship.tool.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

    public EmailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Value("${MAIL_FROM}")
    private String from;

    @Async
    @Override
    public void sendRegistrationEmail(String to, String name) {
        // Prepare Thymeleaf context
        Context ctx = new Context();
        ctx.setVariable("name", name);

        try {
            // Render HTML template
            String html = templateEngine.process("emails/registration", ctx);

            String text = "Hello " + name + ",\n\nThank you for registering at Tool-101.\n\nBest regards,\nTool-101 Team";

            sendMultipartEmail(to, "Welcome to Tool-101!", text, html);
        } catch (Exception ex) {
            // Fallback to plaintext SimpleMailMessage
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(from);
                message.setTo(to);
                message.setSubject("Welcome to Tool-101!");
                message.setText("Hello " + name + ",\n\nThank you for registering at Tool-101.\n\nBest regards,\nTool-101 Team");
                mailSender.send(message);
            } catch (Exception ex2) {
                log.warn("Failed to send registration email to {}: {}", to, ex2.getMessage());
                log.debug("Email send failure details", ex2);
            }
            log.debug("HTML email send failure, used plaintext fallback", ex);
        }
    }

    @Override
    public void sendMultipartEmail(String to, String subject, String textBody, String htmlBody) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            // true = multipart
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);

            // set the plain text as the alternative
            helper.setText(textBody, htmlBody);

            mailSender.send(mimeMessage);
        } catch (Exception ex) {
            log.warn("Failed to send multipart email to {}: {}", to, ex.getMessage());
            log.debug("Multipart email failure details", ex);
            // propagate to caller if needed (here we choose to swallow - consistent with previous behavior)
        }
    }
}
