package com.etaxi.domain.order.dto;

import com.etaxi.core.location.PointSerializer;
import com.etaxi.domain.driver.Driver;
import com.etaxi.domain.order.Order;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    Integer id;

    String driverName;

    String driverContactInfo;

    @JsonSerialize(using = PointSerializer.class)
    Point source;

    @JsonSerialize(using = PointSerializer.class)
    Point destination;

    String transportationTitle;

    LocalDateTime date;

    Double cost;

    Double approximateDuration;

    Boolean isPayed;

    Boolean isDone;

    public OrderResponse(
            Order order,
            Driver driver,
            String transportationTitle) {
        id = order.getId();
        driverName = driver.getName();
        driverContactInfo = driver.getContactInfo();
        source = order.getSource();
        destination = order.getDestination();
        this.transportationTitle = transportationTitle;
        date = order.getDate();
        cost = order.getCost();
        approximateDuration = order.getApproximateDuration();
        isPayed = order.getIsPayed();
        isDone = order.getIsDone();
    }

}
