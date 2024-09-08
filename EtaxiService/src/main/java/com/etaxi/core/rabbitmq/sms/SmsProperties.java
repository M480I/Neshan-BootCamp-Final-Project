package com.etaxi.core.rabbitmq.sms;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SmsProperties {

    String smsExchange;

    String createOrderQueue;
    String paymentQueue;
    String createPassengerDriverQueue;

    String createOrderRoutingKey;
    String paymentRoutingKey;
    String createPassengerDriverRoutingKey;

}
