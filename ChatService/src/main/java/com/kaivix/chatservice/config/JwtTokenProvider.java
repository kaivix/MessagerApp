package com.kaivix.chatservice.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JwtTokenProvider {
    private SecretKey key;

    private long jwtExpirationMs;

    public JwtTokenProvider(@Value("${jwt.secret}") String secret, @Value("${jwt.expirationMs}") long jwtExpirationMs) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.jwtExpirationMs = jwtExpirationMs;
    }

    // Извлечение имени пользователя
    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Проверка валидности токена
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            System.err.println("Неверный JWT: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.err.println("JWT истёк: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.err.println("JWT не поддерживается: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Пустой JWT: " + e.getMessage());
        }
        return false;
    }
}
