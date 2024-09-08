package com.etaxi.core.user.dto;

import lombok.Builder;

@Builder
public record UserSmsDto(
        UserSmsMode mode,
        Integer id,
        String name,
        String contactInfo
) {
}
