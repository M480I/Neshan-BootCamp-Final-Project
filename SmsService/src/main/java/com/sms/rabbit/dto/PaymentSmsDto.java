package com.sms.rabbit.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentSmsDto {

    Integer passenger_id;

    Double cost;

    Integer order_id;

    LocalDateTime date;

}
