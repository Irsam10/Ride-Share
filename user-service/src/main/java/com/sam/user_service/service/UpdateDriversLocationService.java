package com.sam.user_service.service;

import com.ride_dispatch.dto.DriverResponse;
import com.ride_dispatch.dto.LocationDTO;
import com.ride_dispatch.utils.AvailableDriversCache;
import com.sam.user_service.dto.LocationData;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class UpdateDriversLocationService {
// This service is responsible for updating the locations of drivers in the system.
    private final AvailableDriversCache availableDriversCache;
    private RestTemplate restTemplate;
    private static Map<Long, LocationData> availableDriversMap = null;
    public UpdateDriversLocationService(AvailableDriversCache availableDriversCache, RestTemplate restTemplate) {
        this.availableDriversCache = availableDriversCache;
        this.restTemplate = restTemplate;
    }

    public void updateDriverLocations() {

        Map<Long, DriverResponse> activeDrivers = availableDriversCache.getActiveDrivers();
        if (activeDrivers.isEmpty()) {
            return; // No active drivers to update
        }
        // Fetch the latest locations of all active drivers


    }
}
