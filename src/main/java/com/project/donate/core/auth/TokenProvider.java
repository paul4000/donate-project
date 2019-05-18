package com.project.donate.core.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenProvider {

    @Value("${app.jwtSecret}")
    private String secret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInSec;

    private final Logger logger = Logger.getLogger(TokenProvider.class);

    public String generateToken(Authentication authentication) {

        UserDetails user = (UserDetails) authentication.getPrincipal();

        Date expiryDate = new Date(new Date().getTime() + jwtExpirationInSec);

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String obtainUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {

        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
            return true;
        } catch (IllegalArgumentException e) {
            logger.warn("Illegal form of token");
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return false;
    }

}
