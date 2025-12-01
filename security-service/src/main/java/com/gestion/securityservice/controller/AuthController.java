package com.gestion.securityservice.controller;

import com.gestion.securityservice.DTO.AuthRequest;
import com.gestion.securityservice.DTO.JwtResponse;
import com.gestion.securityservice.config.JwtUtils;
import com.gestion.securityservice.entity.AppUser;
import com.gestion.securityservice.entity.Role;
import com.gestion.securityservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {

        if (request.getUsername() == null || request.getEmail() == null ||
                request.getPassword() == null || request.getRole() == null) {
            return ResponseEntity.badRequest().body("All fields are required: username, email, password, role");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        AppUser user = AppUser.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(request.getRole().toUpperCase()))
                .build();

        userRepository.save(user);

        // Créer la réponse avec un message de succès
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User registered successfully");
        response.put("user", Map.of(
                "username", user.getUsername(),
                "email", user.getEmail(),
                "role", user.getRole().name()
        ));

        return ResponseEntity.ok(response);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {

        if (request.getEmail() == null || request.getPassword() == null) {
            return ResponseEntity.badRequest().body("Email and password are required");
        }

        // Authentification
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Récupération de l'utilisateur
        AppUser user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Génération du token
        String token = jwtUtils.generateToken(user.getEmail(), user.getRole().name());

        // Construction de la réponse
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User logged in successfully");
        response.put("user", Map.of(
                "username", user.getUsername(),
                "email", user.getEmail(),
                "role", user.getRole().name(),
                "token", token
        ));

        return ResponseEntity.ok(response);
    }



}
