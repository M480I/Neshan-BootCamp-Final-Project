package com.etaxi.domain.order;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "config.order")
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderProperties {

    Double driverSearchRadius;

}
