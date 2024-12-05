package com.banque.authentication_service.dto;

public class TokenResponse {

    private String token;

    public TokenResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
