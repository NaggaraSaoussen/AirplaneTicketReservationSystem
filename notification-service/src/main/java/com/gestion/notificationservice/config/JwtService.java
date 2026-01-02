package com.gestion.notificationservice.config;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class JwtService {

    private final String securityServiceUrl = "http://localhost:8084/auth/verify";

    public Map<String, Object> verifyToken(String token) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                securityServiceUrl,
                HttpMethod.GET,
                entity,
                Map.class
        );

        System.out.println("VERIFY RESPONSE: " + response.getBody());
        return response.getBody();
    }
}
