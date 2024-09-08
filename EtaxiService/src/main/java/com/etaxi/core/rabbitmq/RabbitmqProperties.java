package com.etaxi.core.rabbitmq;


import com.etaxi.core.rabbitmq.logger.LoggerProperties;
import com.etaxi.core.rabbitmq.sms.SmsProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "config.rabbitmq")
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RabbitmqProperties {

    SmsProperties smsProperties;

    LoggerProperties loggerProperties;

}
