package com.etaxi.core.security.authorization.dto;

import lombok.Builder;

import java.time.LocalDateTime;


@Builder
public record AuthResponse(String username, LocalDateTime dateJoined) {
}
