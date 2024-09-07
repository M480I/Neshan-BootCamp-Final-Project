package com.etaxi.domain.order.dto;

import com.etaxi.core.location.PointSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Builder
public record OrderResponse(

        Integer id,

        String driverName,

        String driverContactInfo,

        @JsonSerialize(using = PointSerializer.class)
        Point source,

        @JsonSerialize(using = PointSerializer.class)
        Point destination,

        String transportationTitle,

        LocalDateTime date,

        Double cost,

        Double approximateDuration,

        Boolean isPayed,

        Boolean isDone

) {
}
