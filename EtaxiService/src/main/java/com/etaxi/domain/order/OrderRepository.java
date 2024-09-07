package com.etaxi.domain.order;

import com.etaxi.domain.order.dto.OrderProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("""
                SELECT
                o.id As id,
                o.driver.name AS driverName,
                o.driver.contactInfo AS driverContactInfo,
                o.transportation.title AS transportationTitle,
                o.source AS source,
                o.destination AS destination,
                o.date AS date,
                o.cost AS cost,
                o.approximateDuration AS approximateDuration,
                o.isPayed AS isPayed,
                o.isDone AS isDone
                FROM Order o
                WHERE o.passenger.id = :passengerId
                    AND (:isPayed IS NULL OR o.isPayed = :isPayed)
                    AND (:isDone IS NULL OR o.isDone = :isDone)
           """)
    List<OrderProjection> findByPassengerId(
            Integer passengerId,
            Boolean isPayed,
            Boolean isDone);

}
