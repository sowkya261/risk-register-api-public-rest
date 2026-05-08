package com.internship.tool.config;

import com.internship.tool.entity.Role;
import com.internship.tool.entity.Tool;
import com.internship.tool.entity.User;
import com.internship.tool.repository.RoleRepository;
import com.internship.tool.repository.ToolRepository;
import com.internship.tool.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private final ToolRepository toolRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (roleRepository.count() == 0) {
            roleRepository.saveAll(Arrays.asList(
                Role.builder().name("ADMIN").build(),
                Role.builder().name("USER").build()
            ));
        }
        if (userRepository.count() == 0) {
            Role adminRole = roleRepository.findByName("ADMIN").orElseThrow();
            Role userRole = roleRepository.findByName("USER").orElseThrow();
            userRepository.saveAll(List.of(
                User.builder().email("admin@tool101.com").fullName("Admin User").password(passwordEncoder.encode("admin1234")).roles(new HashSet<>(List.of(adminRole, userRole))).enabled(true).build(),
                User.builder().email("user1@tool101.com").fullName("User One").password(passwordEncoder.encode("user1234")).roles(new HashSet<>(List.of(userRole))).enabled(true).build(),
                User.builder().email("user2@tool101.com").fullName("User Two").password(passwordEncoder.encode("user1234")).roles(new HashSet<>(List.of(userRole))).enabled(true).build()
            ));
        }
        if (toolRepository.count() == 0) {
            toolRepository.saveAll(List.of(
                Tool.builder().name("Hammer").description("Heavy duty hammer").active(true).build(),
                Tool.builder().name("Screwdriver").description("Flathead screwdriver").active(true).build(),
                Tool.builder().name("Wrench").description("Adjustable wrench").active(true).build(),
                Tool.builder().name("Pliers").description("Needle nose pliers").active(true).build(),
                Tool.builder().name("Drill").description("Cordless drill").active(true).build(),
                Tool.builder().name("Saw").description("Hand saw").active(true).build(),
                Tool.builder().name("Tape Measure").description("25ft tape measure").active(true).build(),
                Tool.builder().name("Level").description("Bubble level").active(true).build(),
                Tool.builder().name("Chisel").description("Wood chisel").active(true).build(),
                Tool.builder().name("Utility Knife").description("Retractable utility knife").active(true).build(),
                Tool.builder().name("Sander").description("Electric sander").active(true).build(),
                Tool.builder().name("Ladder").description("6ft step ladder").active(true).build(),
                Tool.builder().name("Paint Brush").description("2-inch paint brush").active(true).build(),
                Tool.builder().name("Socket Set").description("Metric socket set").active(true).build(),
                Tool.builder().name("Stud Finder").description("Electronic stud finder").active(true).build()
            ));
        }
    }
}package com.internship.tool.config;

import com.internship.tool.entity.Role;
import com.internship.tool.entity.Tool;
import com.internship.tool.entity.User;
import com.internship.tool.repository.RoleRepository;
import com.internship.tool.repository.ToolRepository;
import com.internship.tool.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private final ToolRepository toolRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (roleRepository.count() == 0) {
            roleRepository.saveAll(Arrays.asList(
                Role.builder().name("ADMIN").build(),
                Role.builder().name("USER").build()
            ));
        }
        if (userRepository.count() == 0) {
            Role adminRole = roleRepository.findByName("ADMIN").orElseThrow();
            Role userRole = roleRepository.findByName("USER").orElseThrow();
            userRepository.saveAll(List.of(
                User.builder().email("admin@tool101.com").fullName("Admin User").password(passwordEncoder.encode("admin1234")).roles(new HashSet<>(List.of(adminRole, userRole))).enabled(true).build(),
                User.builder().email("user1@tool101.com").fullName("User One").password(passwordEncoder.encode("user1234")).roles(new HashSet<>(List.of(userRole))).enabled(true).build(),
                User.builder().email("user2@tool101.com").fullName("User Two").password(passwordEncoder.encode("user1234")).roles(new HashSet<>(List.of(userRole))).enabled(true).build()
            ));
        }
        if (toolRepository.count() == 0) {
            toolRepository.saveAll(List.of(
                Tool.builder().name("Hammer").description("Heavy duty hammer").active(true).build(),
                Tool.builder().name("Screwdriver").description("Flathead screwdriver").active(true).build(),
                Tool.builder().name("Wrench").description("Adjustable wrench").active(true).build(),
                Tool.builder().name("Pliers").description("Needle nose pliers").active(true).build(),
                Tool.builder().name("Drill").description("Cordless drill").active(true).build(),
                Tool.builder().name("Saw").description("Hand saw").active(true).build(),
                Tool.builder().name("Tape Measure").description("25ft tape measure").active(true).build(),
                Tool.builder().name("Level").description("Bubble level").active(true).build(),
                Tool.builder().name("Chisel").description("Wood chisel").active(true).build(),
                Tool.builder().name("Utility Knife").description("Retractable utility knife").active(true).build(),
                Tool.builder().name("Sander").description("Electric sander").active(true).build(),
                Tool.builder().name("Ladder").description("6ft step ladder").active(true).build(),
                Tool.builder().name("Paint Brush").description("2-inch paint brush").active(true).build(),
                Tool.builder().name("Socket Set").description("Metric socket set").active(true).build(),
                Tool.builder().name("Stud Finder").description("Electronic stud finder").active(true).build()
            ));
        }
    }
}
