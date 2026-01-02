package com.gestion.reservationservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final RestTemplate restTemplate = new RestTemplate();

    // ✅ ton endpoint réel : /auth/verify
    private final String VERIFY_URL = "http://localhost:8084/auth/verify";

    public Map<String, Object> verifyToken(String token) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token); // => "Authorization: Bearer <token>"

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                VERIFY_URL,
                HttpMethod.GET,
                entity,
                Map.class
        );

        return response.getBody();
    }
}
