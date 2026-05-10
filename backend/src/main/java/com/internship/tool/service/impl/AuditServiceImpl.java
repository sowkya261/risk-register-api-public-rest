package com.internship.tool.service.impl;

import com.internship.tool.entity.AuditEntry;
import com.internship.tool.repository.AuditRepository;
import com.internship.tool.service.AuditService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditServiceImpl implements AuditService {
    private final AuditRepository auditRepository;

    public AuditServiceImpl(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @Override
    public void log(String username, String action, String targetId, String metadata) {
        AuditEntry entry = new AuditEntry(username, action, targetId, metadata);
        auditRepository.save(entry);
    }

    public List<AuditEntry> recent(int limit) {
        return auditRepository.findRecent(PageRequest.of(0, Math.max(1, limit)));
    }
}
