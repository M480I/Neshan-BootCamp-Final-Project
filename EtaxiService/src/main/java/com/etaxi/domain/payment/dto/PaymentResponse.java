package com.etaxi.domain.payment.dto;

import com.etaxi.domain.order.Order;
import com.etaxi.domain.passenger.Passenger;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentResponse {

    String name;

    Integer orderId;

    Double cost;

    public PaymentResponse(Order order, Passenger passenger) {
        name = passenger.getName();
        orderId = order.getId();
        cost = order.getCost();
    }

}
