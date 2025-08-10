package com.sam.publish_ride_service.util;

import com.sam.publish_ride_service.dto.RideRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class RideCache {
    private final List<RideRequest> pendingRide = new CopyOnWriteArrayList<>();

    public void updatePendingRides(List<RideRequest> rides) {
        pendingRide.clear();
        pendingRide.addAll(rides);
    }

    public List<RideRequest> getPendingRides() {
        return pendingRide;
    }
}
