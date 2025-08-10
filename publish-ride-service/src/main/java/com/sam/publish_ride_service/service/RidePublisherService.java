package com.sam.publish_ride_service.service;

import com.sam.publish_ride_service.dto.RideRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RidePublisherService {
    private final KafkaProducer kafkaProducer;
    private final String topicName = "ride-requests";

    public RidePublisherService(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    public void publishRideRequest(RideRequest ride) {
        try {
            // Logic to publish the ride request (e.g., send to a messaging queue or notify riders)
            log.info("Publishing ride request: {}", ride);
            kafkaProducer.sendRide(ride, topicName);
        } catch (Exception e) {
            log.error("Failed to publish ride request: {}", ride.id(), e);
        }
    }
}
