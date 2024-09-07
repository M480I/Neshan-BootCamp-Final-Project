package com.etaxi.domain.driver;

import com.etaxi.core.security.user.User;
import com.etaxi.domain.transportation.Transportation;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.locationtech.jts.geom.Point;
import com.etaxi.core.enums.Gender;

@Entity
@Table(name = "drivers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Driver {

    @Id
    @GeneratedValue
    Integer id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    User user;

    @Column(nullable = false)
    String name;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Transportation transportation;

    @Column(nullable = false)
    String contactInfo;

    @Enumerated(EnumType.STRING)
    Gender gender;

//    @Column(columnDefinition = "geometry")
    Point location;

    Boolean isAvailable = true;

    @PrePersist
    private void prePersist() {
        if (isAvailable == null)
            isAvailable = true;
    }

}
