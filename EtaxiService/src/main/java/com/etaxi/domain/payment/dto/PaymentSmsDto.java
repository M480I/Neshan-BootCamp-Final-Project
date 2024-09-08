package com.etaxi.domain.payment.dto;

import com.etaxi.domain.order.Order;
import com.etaxi.domain.passenger.Passenger;
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

    public PaymentSmsDto(Order order, Passenger passenger) {
        passenger_id = passenger.getId();
        cost = order.getCost();
        order_id = order.getId();
        date = order.getDate();
    }

}
