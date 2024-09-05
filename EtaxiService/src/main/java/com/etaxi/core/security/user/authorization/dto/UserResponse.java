package com.etaxi.core.security.user.authorization.dto;

import lombok.Builder;

import java.time.LocalDateTime;


@Builder
public record UserResponse(String username, LocalDateTime dateJoined) {
}
