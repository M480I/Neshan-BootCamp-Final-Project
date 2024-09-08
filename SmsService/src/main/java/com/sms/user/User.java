package com.sms.user;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue
    Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    UserSmsMode mode;

    @Column(nullable = false)
    Integer modeId;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String contactInfo;

}
