package com.internship.tool.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "audit_entries")
public class AuditEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String action;
    private String targetId;

    @Column(columnDefinition = "text")
    private String metadata;

    private Instant createdAt = Instant.now();

    public AuditEntry() {}

    public AuditEntry(String username, String action, String targetId, String metadata) {
        this.username = username;
        this.action = action;
        this.targetId = targetId;
        this.metadata = metadata;
        this.createdAt = Instant.now();
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getAction() { return action; }
    public String getTargetId() { return targetId; }
    public String getMetadata() { return metadata; }
    public Instant getCreatedAt() { return createdAt; }
}
