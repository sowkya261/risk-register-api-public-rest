// DAY 2 — Entity + Repository Layer
// File: RoleRepository.java
// Purpose: JPA repository for Role entity

package com.internship.tool.repository;

import com.internship.tool.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
