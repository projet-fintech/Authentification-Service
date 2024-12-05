package com.banque.authentication_service.controller;

import com.banque.authentication_service.dto.LoginRequest;
import com.banque.authentication_service.dto.TokenResponse;
import com.banque.authentication_service.entity.AuthUser;
import com.banque.authentication_service.service.AuthService;
import com.banque.authentication_service.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final AuthService authService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest request) {
        try {
           // Validate the username and password
            AuthUser user = authService.validateUser(request.getUsername(), request.getPassword());
            //Generate the Token
            String token = jwtUtil.generateToken(request.getUsername(),"");
            return ResponseEntity.ok(new TokenResponse(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body(new TokenResponse("Invalid credentials"));
        }
    }
}
