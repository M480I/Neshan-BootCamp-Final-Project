package com.etaxi.core.security.token;

import com.etaxi.core.exception.NoTokenInHeaderException;
import com.etaxi.core.security.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtService {

    SecurityProperties properties;

    private SecretKey getSecretKey() {
        byte[] decodedKey = Base64.getDecoder().decode(properties.getSecretKey());
        return Keys.hmacShaKeyFor(decodedKey);
    }

    public Jwt extractTokenFromHeader(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            throw new NoTokenInHeaderException("No Token in the header");
        }
        return getToken(header.substring(properties.getTokenFirstIndex()));
    }

    public Jwt getToken(String token) {

        JwtStatus status = JwtStatus.NULL;
        Claims claims = null;

        try {
            claims = extractClaims(token);
            status = JwtStatus.VALID;
        }
        catch (SignatureException exception) {
            status = JwtStatus.INVALID;
        }
        catch (ExpiredJwtException exception) {
            status = JwtStatus.EXPIRED;
        }

        return new Jwt(token, status, claims);
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Jwt generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails) {
        String token = Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusSeconds(60 * properties.getTokenDuration())))
                .signWith(getSecretKey())
                .claims(extraClaims)
                .compact();
        return new Jwt(token, JwtStatus.VALID, null);
    }

    public Jwt generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<String, Object>(), userDetails);
    }

}
