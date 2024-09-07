package com.etaxi.core.dto;

import lombok.Builder;

@Builder
public record apiResponse<T>(
        String message,
        T content
) {
}
