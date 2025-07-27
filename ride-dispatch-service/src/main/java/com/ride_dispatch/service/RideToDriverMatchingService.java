package com.ride_dispatch.service;

import com.ride_dispatch.dto.DriverResponse;
import com.ride_dispatch.dto.DriverLocation;
import com.ride_dispatch.dto.RideResponse;
import com.ride_dispatch.utils.AvailableDriversCache;
import com.ride_dispatch.utils.KDTree;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RideToDriverMatchingService {
    private List<RideResponse>  rideResponses;
    private AvailableDriversCache availableDriversCache;
    private final String topicName = "ride-requests";
    public RideToDriverMatchingService(AvailableDriversCache availableDriversCache)
    {
        this.availableDriversCache = availableDriversCache;
    }

//    public void matchRideRequests(RideResponse ride) {
//        log.info("Matching ride request: {}", ride);
//        List<DriverLocation> availableDrivers = availableDriversCache.getListOfActiveDriversLocations();
//        KDTree kdTree = new KDTree(availableDrivers);
//        List<DriverLocation> nearestDrivers = kdTree.findNearest(new DriverLocation(ride.passengerId(), ride.startLocationLat(), ride.startLocationLon()), 5);
//        if (nearestDrivers.isEmpty()) {
//            log.info("No available drivers found for ride: {}", ride);
//            return;
//        }
//        log.info("Found {} nearest drivers for ride: {}", nearestDrivers.size(), ride);
//        for (DriverLocation driverLocation : nearestDrivers) {
//            log.info("Driver Location: {}", driverLocation);
//        }
//        for (DriverLocation driverLocation : nearestDrivers) {
//            DriverResponse driver = availableDriversCache.getDriverById(driverLocation.id());
//            if (driver != null) {
//                log.info("Notifying driver {} for ride: {}", driver.id(), ride);
//                // Here you would typically send a notification to the driver
//                // For example, using a messaging system or a REST API call
//                // This is a placeholder for the actual notification logic
//            } else {
//                log.warn("Driver not found in cache for location: {}", driverLocation);
//            }
//        }
//    }

    public void matchRideRequests(RideResponse ride){
        log.info("Matching ride request: {}", ride);
        List<DriverLocation> availableDrivers = availableDriversCache.getListOfActiveDriversLocations();
        KDTree kdTree = new KDTree(availableDrivers);
        List<DriverLocation> nearestDrivers = kdTree.findNearest(new DriverLocation(ride.passengerId(), ride.startLocationLat(), ride.startLocationLon()), 5);
        if (nearestDrivers.isEmpty()) {
            log.info("No available drivers found for ride: {}", ride);
            return;
        }
        log.info("Found {} nearest drivers for ride: {}", nearestDrivers.size(), ride);
        for (DriverLocation driverLocation : nearestDrivers) {
            log.info("Driver Location: {}", driverLocation);
        }
        for (DriverLocation driverLocation : nearestDrivers) {
            DriverResponse driver = availableDriversCache.getDriverById(driverLocation.id());
            if (driver != null) {
                log.info("Notifying driver {} for ride: {}", driver.id(), ride);
                // Here you would typically send a notification to the driver
                // For example, using a messaging system or a REST API call
                // This is a placeholder for the actual notification logic
            } else {
                log.warn("Driver not found in cache for location: {}", driverLocation);
            }
        }
    }

    @KafkaListener(topics = topicName , containerFactory = "rideKafkaListenerContainerFactory")
    public void consume(RideResponse ride) {
        matchRideRequests(ride);
        System.out.println("Received user: " + ride);
    }
}
