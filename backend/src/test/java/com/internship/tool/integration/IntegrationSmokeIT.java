package com.internship.tool.integration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Disabled("Integration tests require Docker/Testcontainers — enable with -Pintegration on machines with Docker")
public class IntegrationSmokeIT {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @Container
    public static GenericContainer<?> redis = new GenericContainer<>("redis:7-alpine").withExposedPorts(6379);

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry reg) {
        reg.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        reg.add("spring.datasource.username", postgreSQLContainer::getUsername);
        reg.add("spring.datasource.password", postgreSQLContainer::getPassword);
        reg.add("spring.redis.host", () -> redis.getHost());
        reg.add("spring.redis.port", () -> redis.getFirstMappedPort());
        // Ensure Flyway runs against the test DB
        reg.add("spring.flyway.enabled", () -> "true");
    }

    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void fullSmokeRegisterLoginListTools() throws Exception {
        String base = "http://localhost:" + port + "/api";

        String email = "ituser" + System.currentTimeMillis() + "@example.com";
        String body = String.format("{\"fullName\":\"IT User\",\"email\":\"%s\",\"password\":\"pass123\"}", email);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<Map<String, Object>> regResp = restTemplate.exchange(base + "/auth/register", HttpMethod.POST, new HttpEntity<>(body, headers), new ParameterizedTypeReference<Map<String, Object>>(){});
        assertEquals(201, regResp.getStatusCode().value());

        // Login
        String loginBody = String.format("{\"email\":\"%s\",\"password\":\"pass123\"}", email);
        ResponseEntity<Map<String, Object>> loginResp = restTemplate.exchange(base + "/auth/login", HttpMethod.POST, new HttpEntity<>(loginBody, headers), new ParameterizedTypeReference<Map<String, Object>>(){});
        assertEquals(200, loginResp.getStatusCode().value());
        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) loginResp.getBody().get("data");
        assertNotNull(data.get("token"));
        String token = (String) data.get("token");

        HttpHeaders auth = new HttpHeaders();
        auth.setBearerAuth(token);
        ResponseEntity<Map<String, Object>> toolsResp = restTemplate.exchange(base + "/tools", HttpMethod.GET, new HttpEntity<>(auth), new ParameterizedTypeReference<Map<String, Object>>(){});
        assertEquals(200, toolsResp.getStatusCode().value());
    }
}
