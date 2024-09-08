package com.etaxi.core.rabbitmq.logger;

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
public class LoggerConfig {

    RabbitmqProperties properties;

    @Bean
    public Exchange loggerExchange() {
        return new FanoutExchange(properties.getLoggerProperties().getLoggerExchange());
    }

    @Bean
    public Queue loggerQueue() {
        return new Queue(properties.getLoggerProperties().getLoggerQueue());
    }

    @Bean
    public Binding loggerBinding() {
        return BindingBuilder
                .bind(loggerQueue())
                .to(loggerExchange())
                .with("")
                .noargs();
    }

}
