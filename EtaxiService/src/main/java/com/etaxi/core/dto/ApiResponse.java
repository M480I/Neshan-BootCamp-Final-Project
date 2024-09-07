package com.etaxi.core.dto;

import lombok.Builder;

@Builder
public record ApiResponse<T>(
        String message,
        T content
) {
}
