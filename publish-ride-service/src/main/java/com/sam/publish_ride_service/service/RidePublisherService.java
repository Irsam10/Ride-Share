package com.sam.publish_ride_service.service;

import com.sam.publish_ride_service.dto.RideResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RidePublisherService {
    public void publishRideRequest(RideResponse ride) {
        try {
            // Logic to publish the ride request (e.g., send to a messaging queue or notify riders)
            log.info("Publishing ride request: {}", ride);
        } catch (Exception e) {
            log.error("Failed to publish ride request: {}", ride.id(), e);
        }
    }
}
