package com.etaxi.domain.driver.dto;

import com.etaxi.core.enums.Gender;
import com.etaxi.core.location.LocationPair;
import lombok.Builder;

@Builder
public record DriverCreateResponse(
        String name,
        String username,
        String transportationTitle,
        String contactInfo,
        Gender gender,
        LocationPair locationPair
) {
}
