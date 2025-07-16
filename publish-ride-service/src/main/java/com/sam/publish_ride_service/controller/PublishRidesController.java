package com.sam.publish_ride_service.controller;

import com.sam.publish_ride_service.dto.RideResponse;
import com.sam.publish_ride_service.util.RideCache;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/publish-ride")
public class PublishRidesController {
    private final RideCache rideCache;

    public PublishRidesController(RideCache rideCache) {
        this.rideCache = rideCache;
    }

    @GetMapping("/getAllPendingRides")
    public List<RideResponse> getAllPendingRides()
    {
        return rideCache.getPendingRides();
    }
}
