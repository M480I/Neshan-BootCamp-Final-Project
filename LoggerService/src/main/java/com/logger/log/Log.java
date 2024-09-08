package com.logger.log;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "logs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Log {

    @Id
    @GeneratedValue
    Integer id;

    @Column(nullable = false)
    String message;

    @Column(nullable = false)
    LocalDateTime date;

}
