package com.etaxi.domain.driver.Dto;

import com.etaxi.core.enums.Gender;
import com.etaxi.core.location.LocationPair;
import com.etaxi.domain.transportation.Transportation;
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
