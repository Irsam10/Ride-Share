package com.sam.publish_ride_service;

import com.sam.ride_service.model.Ride;
import com.sam.ride_service.repository.RideRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class RideRequestPublisherThread implements Runnable {
    private final RideRepository rideRepository;
    private final RidePublisherService ridePublisherService;
    private final long pollingIntervalMillis;

    public RideRequestPublisherThread(RideRepository rideRepository, RidePublisherService ridePublisherService, long pollingIntervalMillis) {
        this.rideRepository = rideRepository;
        this.ridePublisherService = ridePublisherService;
        this.pollingIntervalMillis = pollingIntervalMillis;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Fetch pending ride requests
                List<Ride> pendingRides = rideRepository.findByStatus('P'); // Assuming 'P' is the status for pending rides
                log.info("Found {} pending rides", pendingRides.size());
                for (Ride ride : pendingRides) {
                    // Publish each ride request
                    ridePublisherService.publishRideRequest(ride);
                }

                // Sleep for the polling interval
                Thread.sleep(pollingIntervalMillis);
            } catch (InterruptedException e) {
                log.error("RideRequestPublisherThread interrupted", e);
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                log.error("Error in RideRequestPublisherThread", e);
            }
        }
    }
}