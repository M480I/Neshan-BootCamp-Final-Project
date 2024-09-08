package com.etaxi.domain.passenger;

import com.etaxi.core.enums.Gender;
import com.etaxi.core.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "passengers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Passenger {

    @Id
    @GeneratedValue
    Integer id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    User user;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String contactInfo;

    @Enumerated(EnumType.STRING)
    Gender gender;

}
