package com.banque.authentication_service.service;

import com.banque.authentication_service.entity.AuthUser;
import com.banque.authentication_service.repository.AuthUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthUserRepository authUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(AuthUserRepository authUserRepository, BCryptPasswordEncoder passwordEncoder) {
        this.authUserRepository = authUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthUser validateUser(String username, String password) {
        AuthUser user = authUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return user;
    }
}
