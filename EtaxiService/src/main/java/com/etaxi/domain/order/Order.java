package com.etaxi.domain.order;

import com.etaxi.domain.driver.Driver;
import com.etaxi.domain.passenger.Passenger;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    Driver driver;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    Passenger passenger;

    Boolean isPayed = false;

    @PrePersist
    public void prePersist() {
        if (isPayed == null)
            isPayed = false;
    }

}
