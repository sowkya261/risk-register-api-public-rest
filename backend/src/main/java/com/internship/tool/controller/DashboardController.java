package com.internship.tool.controller;

import com.internship.tool.dto.ApiResponse;
import com.internship.tool.service.AuditService;
import com.internship.tool.service.ToolService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final ToolService toolService;
    private final AuditService auditService;

    public DashboardController(ToolService toolService, AuditService auditService) {
        this.toolService = toolService;
        this.auditService = auditService;
    }

    @GetMapping("/kpis")
    public ResponseEntity<ApiResponse<Map<String, Object>>> kpis() {
        // minimal KPI implementation using the existing toolService
        long totalTools = toolService.getAllTools(null, org.springframework.data.domain.PageRequest.of(0, 1)).getTotalElements();
        // naive last 7 days count (server may need richer queries; for demo use total as fallback)
        long last7 = totalTools; // placeholder for demo

        Map<String, Object> m = new HashMap<>();
        m.put("totalTools", totalTools);
        m.put("toolsLast7Days", last7);
        m.put("activeUsers", 1);
        m.put("pendingReminders", 0);
        return ResponseEntity.ok(new ApiResponse<>(true, "KPIs fetched", m));
    }

    @GetMapping("/export/tools.csv")
    public ResponseEntity<String> exportToolsCsv() {
        StringBuilder sb = new StringBuilder();
        sb.append("id,title,description,active,createdAt\n");
        var page = toolService.getAllTools(null, org.springframework.data.domain.PageRequest.of(0, 100));
        page.forEach(t -> sb.append(t.getId()).append(",\"").append(t.getName().replace("\"","\"\"")).append("\",\"").append((t.getDescription()==null?"":t.getDescription()).replace("\"","\"\"")).append("\",").append(t.getActive()).append(",").append(t.getCreatedAt()).append("\n"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDispositionFormData("attachment", "tools-" + LocalDate.now() + ".csv");
        return ResponseEntity.ok().headers(headers).body(sb.toString());
    }
}
