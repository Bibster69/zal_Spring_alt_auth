package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenGenerator {

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expDate = new Date(currentDate.getTime() + 1234);

        String token = Jwts.builder().setSubject(username).setIssuedAt(currentDate)
                .setExpiration(expDate).signWith(SignatureAlgorithm.ES512, "jwtSecret").compact();

        return token;
    }

    public String getUsername(String token){
        Claims claims = Jwts.parser().setSigningKey(String.valueOf(1234)).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(String.valueOf(1234)).parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("token error");
        }
    }
}
