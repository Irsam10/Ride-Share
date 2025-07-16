package com.sam.publish_ride_service.util;

import com.sam.publish_ride_service.dto.RideResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class RideCache {
    private final List<RideResponse> pendingRide = new CopyOnWriteArrayList<>();

    public void updatePendingRides(List<RideResponse> rides) {
        pendingRide.clear();
        pendingRide.addAll(rides);
    }

    public List<RideResponse> getPendingRides() {
        return pendingRide;
    }
}
