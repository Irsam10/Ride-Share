package com.ride_dispatch.service;

import com.ride_dispatch.dto.RideResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class RideToDriverMatchingThread implements Runnable {
    private final RideToDriverMatchingService rideToDriverMatchingService;
    private final long interval;
    private RestTemplate restTemplate;

    @Value("${app.driver-service-get-pending-services-endpoint }")
    private String getPendingRidesEndpoint;

    public RideToDriverMatchingThread(RideToDriverMatchingService rideToDriverMatchingService,  long interval) {
        this.rideToDriverMatchingService = rideToDriverMatchingService;
        this.restTemplate = new RestTemplate();
        this.interval = interval;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Fetch pending rides from the cache
//                List<RideResponse> pendingRides = restTemplate.exchange(
//                        getPendingRidesEndpoint,
//                        HttpMethod.GET,
//                        null,
//                        new ParameterizedTypeReference<List<RideResponse>>() {}
//                ).getBody();

//                if( pendingRides != null && !pendingRides.isEmpty()) {
//                    // Publish each pending ride
//                    for (RideResponse ride : pendingRides) {
//                        rideToDriverMatchingService.matchRideRequests(ride);
//                    }
//                }
                // Sleep for the specified interval
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break; // Exit the loop if interrupted
            } catch (Exception e) {
                // Handle exceptions appropriately
            }
        }
    }
}
