package com.etaxi.core.security.token;

import org.springframework.stereotype.Component;

@Component
public class JwtMapper {

    public JwtResponse jwtToResponse(Jwt jwt) {
        return JwtResponse.builder()
                .token(jwt.getToken())
                .status(jwt.getStatus().name())
                .build();
    }

}
