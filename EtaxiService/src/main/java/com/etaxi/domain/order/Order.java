package com.etaxi.domain.order;

import com.etaxi.domain.driver.Driver;
import com.etaxi.domain.passenger.Passenger;
import com.etaxi.domain.transportation.Transportation;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {

    @Id
    @GeneratedValue
    Integer id;

    @Column(nullable = false)
    Point source;

    @Column(nullable = false)
    Point destination;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    Driver driver;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    Passenger passenger;

    Double cost;

    Double approximateDuration;

    Boolean isDone = false;

    Boolean isPayed = false;

    LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    Transportation transportation;

    @PrePersist
    private void prePersist() {
        if (isDone == null)
            isDone = false;
        if (isPayed == null)
            isPayed = false;
        if (date == null)
            date = LocalDateTime.now();
    }

}
