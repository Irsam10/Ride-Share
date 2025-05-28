package com.sam.ride_service.dto;

public record RideRequest(Long id, Double startLocationLat,Double startLocationLon, Double endLocationLat,
                          Double endLocationLon,Long passengerId,
                          String passengerName, String vehicleType, String vehicleNumber,
                          String rideType, Double fare, Integer rideDistance) {
}

