package com.sam.publish_ride_service;

import com.sam.ride_service.model.Ride;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RidePublisherService {
    public void publishRideRequest(Ride ride) {
        try {
            // Logic to publish the ride request (e.g., send to a messaging queue or notify riders)
            log.info("Publishing ride request: {}", ride.getId());
        } catch (Exception e) {
            log.error("Failed to publish ride request: {}", ride.getId(), e);
        }
    }
}
