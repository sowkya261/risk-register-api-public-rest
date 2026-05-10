package com.internship.tool.scheduler;

import com.internship.tool.service.EmailService;
import com.internship.tool.service.ToolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import com.internship.tool.dto.ToolDto;

@Component
public class ReminderScheduler {
    private static final Logger log = LoggerFactory.getLogger(ReminderScheduler.class);

    private final ToolService toolService;
    private final EmailService emailService;

    @Value("${MAIL_FROM:admin@localhost}")
    private String adminEmail;

    public ReminderScheduler(ToolService toolService, EmailService emailService) {
        this.toolService = toolService;
        this.emailService = emailService;
    }

    // Runs every day at 8 AM
    @Scheduled(cron = "0 0 8 * * *")
    public void sendDailyReminders() {
        log.info("[Scheduler] Daily reminder job executed.");
        // call service to fetch tools (test verifies this is invoked)
        try {
            // find tools created within the last 24 hours and notify the admin
            Instant since = Instant.now().minus(24, ChronoUnit.HOURS);
            // ToolService currently exposes getAllTools with search and pageable; we'll load all and filter by createdAt if available as text
            // For simplicity, fetch a page of tools and filter by DTO createdAt if present
            List<ToolDto> all = toolService.getAllTools("", Pageable.unpaged()).toList();
            List<ToolDto> recent = all.stream()
                    .filter(t -> t != null && t.getCreatedAt() != null && t.getCreatedAt().isAfter(since))
                    .toList();

            if (!recent.isEmpty()) {
                String subject = "Daily reminder: new tools added";
                StringBuilder text = new StringBuilder();
                StringBuilder html = new StringBuilder();
                text.append("New tools added in the last 24 hours:\n\n");
                html.append("<h3>New tools added in the last 24 hours</h3><ul>");
                for (ToolDto t : recent) {
                    text.append("- ").append(t.getName()).append("\n");
                    html.append("<li>").append(t.getName()).append("</li>");
                }
                html.append("</ul>");

                emailService.sendMultipartEmail(adminEmail, subject, text.toString(), html.toString());
                log.info("Sent reminder email to {} about {} new tools", adminEmail, recent.size());
            } else {
                log.info("No new tools in last 24 hours");
            }
        } catch (Exception ex) {
            log.error("Error while running daily reminders", ex);
        }
    }
}
