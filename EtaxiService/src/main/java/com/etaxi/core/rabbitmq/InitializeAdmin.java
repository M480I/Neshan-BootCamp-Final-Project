package com.etaxi.core.rabbitmq;

import jakarta.annotation.PostConstruct;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitializeAdmin {

    @Autowired
    RabbitAdmin rabbitAdmin;

    // Force RabbitAdmin to declare exchanges, queues, and bindings at startup
    @PostConstruct
    public void initializeAdmin() {
        rabbitAdmin.initialize(); // Manually trigger RabbitAdmin initialization
    }

}
