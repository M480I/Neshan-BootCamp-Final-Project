package com.etaxi.core.rabbitmq.logger;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record LogDto(
        String message,
        LocalDateTime date
) {
}
