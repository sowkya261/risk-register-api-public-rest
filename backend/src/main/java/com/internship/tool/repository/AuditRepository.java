package com.internship.tool.repository;

import com.internship.tool.entity.AuditEntry;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface AuditRepository extends JpaRepository<AuditEntry, Long> {
    @Query("select a from AuditEntry a order by a.createdAt desc")
    List<AuditEntry> findRecent(Pageable pageable);
}
