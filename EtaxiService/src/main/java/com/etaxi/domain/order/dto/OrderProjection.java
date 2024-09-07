package com.etaxi.domain.order.dto;

import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

public interface OrderProjection {
    Integer getId();
    String getDriverName();
    String getDriverContactInfo();
    String getTransportationTitle();
    Point getSource();
    Point getDestination();
    LocalDateTime getDate();
    Double getCost();
    Double getApproximateDuration();
    Boolean getIsPayed();
    Boolean getIsDone();
}
