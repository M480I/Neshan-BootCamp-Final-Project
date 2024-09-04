package com.etaxi.core.security.token;

import lombok.Builder;

@Builder
public record JwtResponse(String token, String status) {
}
