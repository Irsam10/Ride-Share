package com.ride_dispatch.utils;

import com.ride_dispatch.dto.DriverResponse;
import com.ride_dispatch.dto.DriverLocation;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AvailableDriversCache {
    private final Map<Long, DriverResponse> availableDrivers = new ConcurrentHashMap<>();

    public void updateDrivers(List<DriverResponse> newDrivers) {
        Set<Long> incomingIds = new HashSet<>();
        for (DriverResponse driver : newDrivers) {
            incomingIds.add(driver.id());
            availableDrivers.put(driver.id(), driver); // Insert or update
        }

        // Remove drivers not in the new list (stale ones)
        availableDrivers.keySet().removeIf(id -> !incomingIds.contains(id));
    }

    public Map<Long,DriverResponse> getActiveDrivers() {
        return availableDrivers;
    }
    public DriverResponse getDriverById(Long id) {
        return availableDrivers.get(id);
    }
    public List<DriverResponse> getListOfActiveDrivers() {
        return List.copyOf(availableDrivers.values());
    }

    public List<DriverLocation> getListOfActiveDriversLocations() {
        return availableDrivers.values().stream()
                .map(driver -> new DriverLocation(driver.id(), driver.lat(), driver.lon()))
                .toList();
    }
}