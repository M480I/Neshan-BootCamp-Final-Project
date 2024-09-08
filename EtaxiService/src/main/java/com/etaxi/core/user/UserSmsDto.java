package com.etaxi.core.user;

import lombok.Builder;

@Builder
public record UserSmsDto(
        Integer id,
        String name,
        String contactInfo
) {
}
