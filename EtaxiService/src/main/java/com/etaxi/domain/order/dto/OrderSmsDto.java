package com.etaxi.domain.order.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record OrderSmsDto(
        Integer passengerId,
        Integer driverId,
        String transportationTitle,
        Double cost,
        Double approximateDuration,
        LocalDateTime date
) {
}
