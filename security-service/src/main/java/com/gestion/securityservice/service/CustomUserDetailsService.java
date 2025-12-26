package com.gestion.securityservice.service;

import com.gestion.securityservice.entity.AppUser;
import com.gestion.securityservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail()) // email = username
                .password(user.getPassword())
                .authorities("ROLE_" + user.getRole().name()) // ⚠️ hasRole("ADMIN") → ROLE_ADMIN
                .build();
    }
}

