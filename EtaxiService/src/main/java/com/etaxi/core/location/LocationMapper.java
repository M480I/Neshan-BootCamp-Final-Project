package com.etaxi.core.location;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LocationMapper {

    GeometryFactory geometryFactory;

    public Point locationPairToPoint(LocationPair locationPair) {
        Coordinate coordinate = new Coordinate(
                locationPair.longitude(),
                locationPair.latitude()
        );
        return geometryFactory.createPoint(coordinate);
    }

    public LocationPair pointToLocationPair(Point point) {
        return new LocationPair(point.getX(), point.getY());
    }

}
