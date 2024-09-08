package com.etaxi.core.rabbitmq.sms;

import com.etaxi.core.rabbitmq.RabbitmqProperties;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SmsConfig {

    RabbitmqProperties properties;

    @Bean
    public Exchange smsExchange() {
        return new TopicExchange(properties.getSmsProperties().getSmsExchange());
    }

    @Bean
    public Queue createOrderQueue() {
        return new Queue(properties.getSmsProperties().getCreateOrderQueue());
    }

    @Bean
    public Queue paymentQueue() {
        return new Queue(properties.getSmsProperties().getPaymentQueue());
    }

    @Bean
    public Queue createPassengerDriverQueue() {
        return new Queue(properties.getSmsProperties().getCreatePassengerDriverQueue());
    }

    @Bean
    public Binding createOrderBinding() {
        return BindingBuilder
                .bind(createOrderQueue())
                .to(smsExchange())
                .with(properties.getSmsProperties().getCreateOrderRoutingKey())
                .noargs();
    }

    @Bean
    public Binding paymentBinding() {
        return BindingBuilder
                .bind(paymentQueue())
                .to(smsExchange())
                .with(properties.getSmsProperties().getPaymentRoutingKey())
                .noargs();
    }

    @Bean
    public Binding createPassengerDriverBinding() {
        return BindingBuilder
                .bind(createPassengerDriverQueue())
                .to(smsExchange())
                .with(properties.getSmsProperties().getCreatePassengerDriverRoutingKey())
                .noargs();
    }

}
