package com.ride_dispatch;

import com.ride_dispatch.service.AvailableDriverService;
import com.ride_dispatch.service.AvailableDriverServiceThread;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AvailableDriverServiceStarter {
    private Thread thread;
    private AvailableDriverService availableDriverService;

    public  AvailableDriverServiceStarter(AvailableDriverService availableDriverService) {
        this.availableDriverService = availableDriverService;
    }
    @PostConstruct
    public void startThread()
    {
        log.info("Starting Available Driver Info Thread");
        AvailableDriverServiceThread availableDriverRunnable =
                new AvailableDriverServiceThread(availableDriverService,5000);
        thread = new Thread(availableDriverRunnable, "RideRequestPublisherThread");
        thread.start();
    }

    @PreDestroy
    public void stopThread() {
        log.warn("Interrupting available Driver Info Thread");
        if (thread != null && thread.isAlive()) {
            thread.interrupt();
        }
    }
}