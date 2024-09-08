package com.etaxi.core.rabbitmq.sms;

import com.etaxi.core.rabbitmq.RabbitmqProperties;
import com.etaxi.core.user.UserSmsDto;
import com.etaxi.domain.order.dto.OrderSmsDto;
import com.etaxi.domain.payment.dto.PaymentSmsDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SmsProducer {

    RabbitmqProperties properties;
    RabbitTemplate template;

    public void createOrder(OrderSmsDto orderDto) {
        template.convertAndSend(
                properties.getSmsProperties().getSmsExchange(),
                properties.getSmsProperties().getCreateOrderRoutingKey(),
                orderDto
        );
    }

    public void payment(PaymentSmsDto paymentDto) {
        template.convertAndSend(
                properties.getSmsProperties().getSmsExchange(),
                properties.getSmsProperties().getPaymentRoutingKey(),
                paymentDto
        );
    }

    public void createDriverOrPassenger(UserSmsDto userSmsDto) {
        template.convertAndSend(
                properties.getSmsProperties().getSmsExchange(),
                properties.getSmsProperties().getCreatePassengerDriverRoutingKey(),
                userSmsDto
        );
    }

}
