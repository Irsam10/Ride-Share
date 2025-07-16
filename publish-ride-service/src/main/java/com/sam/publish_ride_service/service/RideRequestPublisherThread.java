package com.sam.publish_ride_service.service;

import com.sam.publish_ride_service.dto.RideResponse;
import com.sam.publish_ride_service.util.Constants;
import com.sam.publish_ride_service.util.RideCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    private RideCache rideCache;

    @Value("${app.ride-service-endpoint}")
    private String publishRideEndpoint;

    @Value("${app.ride-service-update-rides-status-endpoint}")
    private String updateRideStatusEndpoint;

    public RideRequestPublisherThread( RidePublisherService ridePublisherService, RideCache rideCache,  long pollingIntervalMillis) {
        this.restTemplate = new RestTemplate();
        this.ridePublisherService = ridePublisherService;
        this.rideCache = rideCache;
        this.pollingIntervalMillis = pollingIntervalMillis;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Fetch pending ride requests
                log.info("Polling for pending ride requests...");
                List<RideResponse> response = List.of((RideResponse) Objects.requireNonNull(restTemplate.exchange(
                        updateRideStatusEndpoint + "?fromStatus={from}&toStatus={to}",
                        HttpMethod.PUT,
                        null,
                        new ParameterizedTypeReference<>() {},
                        Constants.PENDING,  // fromStatus
                        Constants.IN_PROGRESS  // toStatus
                ).getBody()));

                log.info("Found {} pending rides", response.size());

                rideCache.updatePendingRides(response);
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