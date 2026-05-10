package com.internship.tool.controller;

import com.internship.tool.dto.ApiResponse;
import com.internship.tool.entity.AuditEntry;
import com.internship.tool.service.impl.AuditServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
public class AuditController {
    private final AuditServiceImpl auditService;

    public AuditController(AuditServiceImpl auditService) {
        this.auditService = auditService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AuditEntry>>> recent(@RequestParam(defaultValue = "10") int limit) {
        List<AuditEntry> entries = auditService.recent(limit);
        return ResponseEntity.ok(new ApiResponse<>(true, "Recent audit entries", entries));
    }
}
