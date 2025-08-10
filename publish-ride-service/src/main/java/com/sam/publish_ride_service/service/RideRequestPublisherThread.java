package com.sam.publish_ride_service.service;

import com.sam.publish_ride_service.dto.RideRequest;
import com.sam.publish_ride_service.dto.RideResponse;
import com.sam.publish_ride_service.util.Constants;
import com.sam.publish_ride_service.util.RideCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Slf4j
public class RideRequestPublisherThread implements Runnable{
    private final RidePublisherService ridePublisherService;
    private final long pollingIntervalMillis;
    private RestTemplate restTemplate;
    private RideCache rideCache;
    private String updateRideStatusEndpoint;

    public RideRequestPublisherThread( RidePublisherService ridePublisherService, RideCache rideCache,  long pollingIntervalMillis, String updateRideStatusEndpoint) {
        this.restTemplate = new RestTemplate();
        this.ridePublisherService = ridePublisherService;
        this.rideCache = rideCache;
        this.pollingIntervalMillis = pollingIntervalMillis;
        this.updateRideStatusEndpoint = updateRideStatusEndpoint;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Fetch pending ride requests
                log.info("Polling for pending ride requests...");
                log.info("URL : {}", updateRideStatusEndpoint+ "?fromStatus={from}&toStatus={to}");
                ResponseEntity<List<RideRequest>> response = restTemplate.exchange(
                        updateRideStatusEndpoint + "?fromStatus={from}&toStatus={to}",
                        HttpMethod.PUT,
                        null,
                        new ParameterizedTypeReference<>() {},
                        Constants.PENDING,  // fromStatus
                        Constants.PENDING  // toStatus -- temporarily changed to PENDING so I don't have to change it again and again to execute the case again, change to IN_PROGRESS after
                );
                List<RideRequest> rideRequests = Objects.requireNonNull(response.getBody());
//                List<RideResponse> response = rideRequests.stream()
//                        .map(req -> new RideResponse(
//                               req.id(),
//                                req.startLocationLat(),
//                                req.startLocationLon(),
//                                req.endLocationLat(),
//                                req.endLocationLon(),
//                                req.passengerId(),
//                                req.passengerName(),
//                                req.vehicleType(),
//                                req.vehicleNumber(),
//                                req.rideType(),
//                                req.fare(),
//                                req.rideDistance()
//                        ))
//                        .toList();
                log.info("Found {} pending rides", rideRequests.size());

                rideCache.updatePendingRides(rideRequests);
                for (RideRequest ride : rideRequests) {
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
