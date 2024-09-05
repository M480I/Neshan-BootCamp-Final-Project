package com.etaxi.domain.passenger.dto;

import com.etaxi.core.enums.Gender;
import lombok.Builder;

@Builder
public record PassengerCreateResponse(
        String username,
        String name,
        String contactInfo,
        Gender gender
) {
}
