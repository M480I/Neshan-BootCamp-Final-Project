package com.etaxi.domain.driver;

import com.etaxi.domain.transportation.Transportation;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {

    @Query(value = """
         SELECT *
         FROM drivers d
         WHERE ((:radius IS NULL)
                    OR (ST_DWithin(CAST(d.location AS geography), CAST(:location AS geography), :radius)))
                AND d.is_available = true
                AND d.transportation_id = :transportationId
         ORDER BY ST_Distance(d.location, :location) ASC
         LIMIT 1
         """, nativeQuery = true)
    Optional<Driver> findNearestDriver(
            Point location,
            Integer transportationId,
            Double radius);

}
