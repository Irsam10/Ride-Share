package com.sam.ride_service.controller;

import com.sam.ride_service.dto.RideCancelRequest;
import com.sam.ride_service.dto.RideRequest;
import com.sam.ride_service.service.RideService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ride")
public class RideController {
    private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    @PostMapping("/create-ride")
    public ResponseEntity<String> createRide(@RequestBody RideRequest rideRequest) {
        try {
            rideService.createRide(rideRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("Ride created successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ride creation failed: " + e.getMessage());
        }
    }

    @GetMapping
    public RideRequest getRide(Long Id) {
        return rideService.getRide(Id);
    }
    @GetMapping("/getRideByPassengerId")
    public List<RideRequest> getRidesByPassengerId(Long passengerId) {
        return rideService.getRidesByPassengerId(passengerId);
    }
    @GetMapping("/getRideByDriverId")
    public List<RideRequest> getRidesByDriverId(Long driverId) {
        return rideService.getRidesByDriverId(driverId);
    }
    @PostMapping("/cancel")
    public String cancelRide(@RequestBody RideCancelRequest rideCancelRequest) {
        rideService.cancelRequest(rideCancelRequest);
        return "Ride cancelled";
    }
}
