package com.internship.tool.service;

public interface AuditService {
    void log(String username, String action, String targetId, String metadata);
}
