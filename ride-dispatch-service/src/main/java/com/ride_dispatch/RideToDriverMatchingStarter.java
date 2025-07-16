package com.ride_dispatch;

import com.ride_dispatch.service.RideToDriverMatchingService;
import com.ride_dispatch.service.RideToDriverMatchingThread;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RideToDriverMatchingStarter {
    private Thread thread;
    private RideToDriverMatchingService rideToDriverMatchingService;

    public  RideToDriverMatchingStarter(RideToDriverMatchingService rideToDriverMatchingService) {
        this.rideToDriverMatchingService = rideToDriverMatchingService;
    }
    @PostConstruct
    public void startThread()
    {
        log.info("Starting Ride To Driver Matching Request Thread");
        RideToDriverMatchingThread publisherRunnable =
                new RideToDriverMatchingThread(rideToDriverMatchingService,5000);
        thread = new Thread(publisherRunnable, "RideRequestPublisherThread");
        thread.start();
    }

    @PreDestroy
    public void stopThread() {
        log.warn("Interrupting Ride To Driver Matching Request Thread");
        if (thread != null && thread.isAlive()) {
            thread.interrupt();
        }
    }
}
