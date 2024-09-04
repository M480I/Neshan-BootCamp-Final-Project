package com.etaxi.core.security.token;

import io.jsonwebtoken.Claims;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Jwt {

    String token;
    JwtStatus status;
    Claims claims;

}

