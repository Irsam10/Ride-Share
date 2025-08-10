package com.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling  // Optional: Only needed if you want scheduled message production
public class Main {

    private final Producer producer;

    // Constructor injection
    public Main(Producer producer) {
        this.producer = producer;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    // Optional: Send a test message when application starts
    @EventListener(ApplicationReadyEvent.class)
    public void doAfterStartup() {
        producer.sendMessage("Application started! Sending first test message.");
    }

    // Optional: Scheduled message producer example
    @Scheduled(fixedRate = 5000)  // Send every 5 seconds
    public void scheduledMessage() {
        producer.sendMessage("Scheduled message at: " + System.currentTimeMillis());
    }
}