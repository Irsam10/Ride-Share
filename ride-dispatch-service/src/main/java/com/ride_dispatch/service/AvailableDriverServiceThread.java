package com.ride_dispatch.service;

public class AvailableDriverServiceThread implements Runnable{
    private final AvailableDriverService availableDriverService;
    private final long interval;

    public AvailableDriverServiceThread(AvailableDriverService availableDriverService, long interval) {
        this.availableDriverService = availableDriverService;
        this.interval = interval;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                availableDriverService.getAvailableDrivers();
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupted status
            }
        }
    }
}
