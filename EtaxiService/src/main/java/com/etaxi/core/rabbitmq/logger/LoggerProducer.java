package com.etaxi.core.rabbitmq.logger;

import com.etaxi.core.rabbitmq.RabbitmqProperties;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoggerProducer {

    RabbitmqProperties properties;
    RabbitTemplate template;

    public void storeLog(LogDto logDto) {
        template.convertAndSend(
                properties.getLoggerProperties().getLoggerExchange(),
                "",
                logDto
        );
    }
}
