package com.etaxi.domain.transportation;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "transportations")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Transportation {

    @Id
    @GeneratedValue
    Integer id;

    @Column(nullable = false, unique = true)
    String title;

    @Column(nullable = false)
    Double speed;

    @Column(nullable = false)
    Double pricePerKiloMeter;

}
