package com.sam.publish_ride_service;

import com.sam.publish_ride_service.service.RidePublisherService;
import com.sam.publish_ride_service.service.RideRequestPublisherThread;
import com.sam.publish_ride_service.util.RideCache;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class RideRequestPublisherStarter {
    private final RidePublisherService ridePublisherService;
    private final RideCache rideCache;
    private Thread thread;
    @Value("${app.ride-service-update-rides-status-endpoint}")
    private String updateRideStatusEndpoint;

    public RideRequestPublisherStarter(RidePublisherService ridePublisherService, RideCache rideCache) {
        this.ridePublisherService = ridePublisherService;
        this.rideCache = rideCache;
    }

    @PostConstruct
    public void startThread() {
        log.info("Starting Publish Ride Request Thread");
        RideRequestPublisherThread publisherRunnable =
                new RideRequestPublisherThread(ridePublisherService, rideCache, 5000,updateRideStatusEndpoint);
        thread = new Thread(publisherRunnable, "RideRequestPublisherThread");
        thread.start();
    }

    @PreDestroy
    public void stopThread() {
        log.warn("Interrupting Publish Ride Request Thread");
        if (thread != null && thread.isAlive()) {
            thread.interrupt();
        }
    }
}
