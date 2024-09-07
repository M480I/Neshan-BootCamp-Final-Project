package com.etaxi.core.location;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LocationUtils {

    public Double calculateDistance(Point point1, Point point2) {
        if (point1 != null && point2 != null) {
            if (!point1.isEmpty() && !point2.isEmpty()) {
                double distanceInDegrees = point1.distance(point2);
                double averageLatitude = (point1.getY() + point2.getY()) / 2;
                double metersPerDegreeLongitude = 111320 * Math.cos(Math.toRadians(averageLatitude));
                double metersPerDegreeLatitude = 111320;
                double deltaLongitude = point2.getX() - point1.getX();
                double deltaLatitude = point2.getY() - point1.getY();
                double distanceX = deltaLongitude * metersPerDegreeLongitude;
                double distanceY = deltaLatitude * metersPerDegreeLatitude;
                return Math.sqrt(distanceX * distanceX + distanceY * distanceY);
            }
            else {
                return 0.0;
            }
        }
        else {
            throw new IllegalArgumentException("null geometries are not supported");
        }
    }

}
