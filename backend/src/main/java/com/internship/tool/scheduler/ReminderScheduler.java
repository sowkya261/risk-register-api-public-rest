package com.internship.tool.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReminderScheduler {
    // Runs every day at 8 AM
    @Scheduled(cron = "0 0 8 * * *")
    public void sendDailyReminders() {
        log.info("[Scheduler] Daily reminder job executed.");
        // Add reminder logic here (e.g., send summary emails, clean up, etc.)
    }
}
