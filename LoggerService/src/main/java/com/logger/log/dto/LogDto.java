package com.logger.log.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record LogDto(
        String message,
        LocalDateTime date
) {
}
