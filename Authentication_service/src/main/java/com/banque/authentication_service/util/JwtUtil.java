package com.banque.authentication_service.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "2b6f9324ef6cb937330eaf05d16f6379ba3254c0901e89ddb248a21a61084288f7f8e0df76c42952aa609dd555aa393c3377c9996de5795163fc361a8bbfd0840182463b514fc28cc21c52bf1c4a3aaa6c84ffa20d770547df85ff76fbbfe07dadda35e16c72ba8569f8ac119cad11411270771de0954cb3814423a72070a5562b2a64c0b836e028394dc1cbe3df6e12b7692a2cd939ae85315111966861b6e3c63ed452cc85a5236bb6d7e161114e315085a00991844c724f993f70cc2468f2a75ec12d3ac8a8faff0969eb24f4a977233ffeb4f77ca16cb44bcbd6f4140642e201958e382a7abd11c7be0032f1c45852159c9edeb0c285d38287d05f44871904ccbdcd6ff67eed583b619f3bfbc84c00152300e9dea4d8595ad56dd6f2e0fc29d38e754826b384bc5244bfde5c6836d46d877ee3bedcde242d80bec78d04ab";
    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1H

    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role",role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
                .compact();
    }

    public Claims validateToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
