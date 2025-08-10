package com.sam.user_service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UpdateDriversLocationStarter {
    private Thread thread;
//    private UpdateDriversLocationService updateDriversLocationService;

//    public UpdateDriversLocationStarter(UpdateDriversLocationService updateDriversLocationService) {
//        this.updateDriversLocationService = updateDriversLocationService;
//    }
//    @PostConstruct
//    public void startThread()
//    {
//        log.info("Starting Updating-Driver-Location Thread");
//        UpdateDriversLocationThread publisherRunnable =
//                new UpdateDriversLocationThread(updateDriversLocationService,5000);
//        thread = new Thread(publisherRunnable, "UpdateDriversLocationThread");
//        thread.start();
//    }

    @PreDestroy
    public void stopThread() {
        log.warn("Interrupting Updating-Driver-Location Thread");
        if (thread != null && thread.isAlive()) {
            thread.interrupt();
        }
    }
}