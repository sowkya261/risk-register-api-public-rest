// DAY 2 — Entity + Repository Layer
// File: Role.java
// Purpose: JPA entity for user roles (ADMIN, USER)

package com.internship.tool.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 32)
    private String name;
}
