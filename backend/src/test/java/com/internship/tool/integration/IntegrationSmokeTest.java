package com.internship.tool.integration;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled("Integration tests require Docker/Testcontainers — enable with -Pintegration on machines with Docker")
public class IntegrationSmokeTest {

    @Test
    void placeholder() {
        // Disabled placeholder to avoid running Testcontainers in environments without Docker.
    }
}
