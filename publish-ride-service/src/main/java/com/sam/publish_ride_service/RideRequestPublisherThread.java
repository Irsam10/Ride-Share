package com.sam.publish_ride_service;

import com.sam.publish_ride_service.dto.RideResponse;
import com.sam.publish_ride_service.service.RidePublisherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Slf4j
public class RideRequestPublisherThread implements Runnable {
    private final RidePublisherService ridePublisherService;
    private final long pollingIntervalMillis;
    private RestTemplate restTemplate;

    public RideRequestPublisherThread( RidePublisherService ridePublisherService, long pollingIntervalMillis) {
        this.restTemplate = new RestTemplate();
        this.ridePublisherService = ridePublisherService;
        this.pollingIntervalMillis = pollingIntervalMillis;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String url = "http://localhost:8081/api/ride/getRidesByStatus?status={status}"; // URL to fetch pending rides
                // Fetch pending ride requests
                log.info("Polling for pending ride requests...");
                List<RideResponse> response = List.of(Objects.requireNonNull(restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        RideResponse[].class,
                        'P' // Assuming 'P' is the status for pending rides
                ).getBody()));
                log.info("Found {} pending rides", response.size());
                for (RideResponse ride : response) {
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