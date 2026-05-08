// DAY 2 — Entity + Repository Layer
// File: ToolRepository.java
// Purpose: JPA repository for Tool entity

package com.internship.tool.repository;

import com.internship.tool.entity.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ToolRepository extends JpaRepository<Tool, Long> {
    Optional<Tool> findByName(String name);
}
