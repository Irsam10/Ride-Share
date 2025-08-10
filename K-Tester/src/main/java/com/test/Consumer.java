package com.test;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    @KafkaListener(topics = "test-requests", groupId = "my-consumer-group")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }
}