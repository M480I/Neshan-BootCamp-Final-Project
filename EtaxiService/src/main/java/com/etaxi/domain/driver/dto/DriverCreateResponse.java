package com.etaxi.domain.driver.dto;

import com.etaxi.core.enums.Gender;
import com.etaxi.core.location.PointSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import org.locationtech.jts.geom.Point;

@Builder
public record DriverCreateResponse(
        String name,
        String username,
        String transportationTitle,
        String contactInfo,
        Gender gender,
        @JsonSerialize(using = PointSerializer.class)
        Point location,
        Boolean isAvailable
) {
}
