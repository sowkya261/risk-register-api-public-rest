package com.internship.tool.config;

import com.internship.tool.entity.Role;
import com.internship.tool.entity.Tool;
import com.internship.tool.entity.User;
import com.internship.tool.repository.RoleRepository;
import com.internship.tool.repository.ToolRepository;
import com.internship.tool.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {
    private final ToolRepository toolRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(ToolRepository toolRepository, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.toolRepository = toolRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (roleRepository.count() == 0) {
            roleRepository.saveAll(Arrays.asList(
                new Role("ADMIN"),
                new Role("USER")
            ));
        }
        if (userRepository.count() == 0) {
            Role adminRole = roleRepository.findByName("ADMIN").orElseThrow();
            Role userRole = roleRepository.findByName("USER").orElseThrow();
            userRepository.saveAll(List.of(
                new User("admin@tool101.com", passwordEncoder.encode("admin1234"), "Admin User", new HashSet<>(List.of(adminRole, userRole)), true),
                new User("user1@tool101.com", passwordEncoder.encode("user1234"), "User One", new HashSet<>(List.of(userRole)), true),
                new User("user2@tool101.com", passwordEncoder.encode("user1234"), "User Two", new HashSet<>(List.of(userRole)), true)
            ));
        }
        if (toolRepository.count() == 0) {
            toolRepository.saveAll(List.of(
                new Tool("Hammer", "Heavy duty hammer", true),
                new Tool("Screwdriver", "Flathead screwdriver", true),
                new Tool("Wrench", "Adjustable wrench", true),
                new Tool("Pliers", "Needle nose pliers", true),
                new Tool("Drill", "Cordless drill", true),
                new Tool("Saw", "Hand saw", true),
                new Tool("Tape Measure", "25ft tape measure", true),
                new Tool("Level", "Bubble level", true),
                new Tool("Chisel", "Wood chisel", true),
                new Tool("Utility Knife", "Retractable utility knife", true),
                new Tool("Sander", "Electric sander", true),
                new Tool("Ladder", "6ft step ladder", true),
                new Tool("Paint Brush", "2-inch paint brush", true),
                new Tool("Socket Set", "Metric socket set", true),
                new Tool("Stud Finder", "Electronic stud finder", true)
            ));
        }
    }
}
