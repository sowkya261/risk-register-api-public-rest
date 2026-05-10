package com.internship.tool.service;

public interface EmailService {
    void sendRegistrationEmail(String to, String name);

    /**
     * Send a reminder or arbitrary email with both plaintext and HTML parts.
     */
    void sendMultipartEmail(String to, String subject, String textBody, String htmlBody);
}
