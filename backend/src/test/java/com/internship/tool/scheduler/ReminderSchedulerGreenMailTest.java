package com.internship.tool.scheduler;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;
import com.internship.tool.dto.ToolDto;
import com.internship.tool.service.EmailService;
import com.internship.tool.service.ToolService;
import com.internship.tool.service.impl.EmailServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.Instant;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReminderSchedulerGreenMailTest {

    @Test
    public void scheduler_sends_email_via_greenmail() throws Exception {
        GreenMail greenMail = new GreenMail(ServerSetupTest.SMTP);
        try {
            greenMail.start();
            // configure JavaMailSender to use GreenMail SMTP
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost("localhost");
            mailSender.setPort(greenMail.getSmtp().getPort());
        mailSender.setProtocol("smtp");
        mailSender.setUsername("");
        mailSender.setPassword("");
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "false");
        props.put("mail.smtp.starttls.enable", "false");

            // mock TemplateEngine using Mockito
            TemplateEngine templateEngine = mock(TemplateEngine.class);
            when(templateEngine.process(org.mockito.ArgumentMatchers.anyString(), org.mockito.ArgumentMatchers.any(Context.class)))
                    .thenReturn("<p>Test HTML</p>");

            EmailService emailService = new EmailServiceImpl(mailSender, templateEngine);
            // set 'from' on EmailServiceImpl (it's injected in runtime via @Value)
            java.lang.reflect.Field fromField = EmailServiceImpl.class.getDeclaredField("from");
            fromField.setAccessible(true);
            fromField.set(emailService, "noreply@localhost");

        // mock ToolService to return a single recent tool
        ToolService toolService = mock(ToolService.class);
        ToolDto dto = new ToolDto(1L, "Sample Tool", "desc", true);
        dto.setCreatedAt(Instant.now());
        when(toolService.getAllTools("", org.springframework.data.domain.Pageable.unpaged()))
                .thenReturn(new org.springframework.data.domain.PageImpl<>(List.of(dto)));

        ReminderScheduler scheduler = new ReminderScheduler(toolService, emailService);
        // set adminEmail via reflection (private field)
        java.lang.reflect.Field f = ReminderScheduler.class.getDeclaredField("adminEmail");
        f.setAccessible(true);
        f.set(scheduler, "admin@localhost");

        // run the scheduler
        scheduler.sendDailyReminders();

            // give GreenMail a moment
            Thread.sleep(200);

            assertThat(greenMail.getReceivedMessages()).hasSizeGreaterThanOrEqualTo(1);
        } finally {
            greenMail.stop();
        }
    }
}
