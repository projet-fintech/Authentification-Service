package com.banque.authentication_service.service;

import com.banque.authentication_service.entity.AuthUser;
import com.banque.authentication_service.repository.AuthUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private AuthUserRepository authUserRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    private AuthUser authUser;

    @BeforeEach
    void setUp() {
        authUser = new AuthUser(UUID.randomUUID(),UUID.randomUUID(), "testuser", "encodedPassword", "USER");
    }

    @Test
    void validateUser_should_return_user_when_credentials_are_valid() {
        when(authUserRepository.findByUsername("testuser")).thenReturn(Optional.of(authUser));
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);

        AuthUser validatedUser = authService.validateUser("testuser", "password");
        assertNotNull(validatedUser);
        assertEquals(authUser.getUsername(), validatedUser.getUsername());
        assertEquals(authUser.getId(), validatedUser.getId());
    }


    @Test
    void validateUser_should_throw_exception_when_user_not_found() {
        when(authUserRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> {
            authService.validateUser("testuser", "password");
        });
    }

    @Test
    void validateUser_should_throw_exception_when_password_invalid(){
        when(authUserRepository.findByUsername("testuser")).thenReturn(Optional.of(authUser));
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(false);
        assertThrows(RuntimeException.class, () -> {
            authService.validateUser("testuser", "password");
        });
    }
}
