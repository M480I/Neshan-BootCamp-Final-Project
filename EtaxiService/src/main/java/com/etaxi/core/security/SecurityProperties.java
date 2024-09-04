package com.etaxi.core.security;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "config.security")
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SecurityProperties {
    Integer tokenFirstIndex;
    String SecretKey;
    Long tokenDuration;
}
