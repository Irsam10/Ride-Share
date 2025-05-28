package com.sam.publish_ride_service;

import com.sam.ride_service.repository.RideRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class RideRequestPublisherStarter {
    private final RideRepository rideRepository;
    private final RidePublisherService ridePublisherService;

    private Thread thread;

    public RideRequestPublisherStarter(RideRepository rideRepository, RidePublisherService ridePublisherService) {
        this.rideRepository = rideRepository;
        this.ridePublisherService = ridePublisherService;
    }

    @PostConstruct
    public void startThread() {
        log.info("Starting Publish Ride Request Thread");
        RideRequestPublisherThread publisherRunnable =
                new RideRequestPublisherThread(rideRepository, ridePublisherService, 5000);
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
