package com.smartcommerce.user_service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    @KafkaListener(
            topics = "order-topic",
            groupId = "notification-group"
    )
    public void consumeOrderEvent(String message) {

        System.out.println("=================================");
        System.out.println("ORDER EVENT RECEIVED");
        System.out.println(message);
        System.out.println("Sending email notification...");
        System.out.println("=================================");
    }
}