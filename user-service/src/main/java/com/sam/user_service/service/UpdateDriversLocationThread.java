package com.sam.user_service.service;

public class UpdateDriversLocationThread implements Runnable{
    private final UpdateDriversLocationService updateDriversLocationService;
    private final long interval;

    public UpdateDriversLocationThread(UpdateDriversLocationService updateDriversLocationService, long interval) {
        this.updateDriversLocationService = updateDriversLocationService;
        this.interval = interval;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                updateDriversLocationService.updateDriverLocations();
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupted status
            }
        }
    }
}
