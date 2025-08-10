package com.sam.publish_ride_service.dto;

public record RideResponse(Long id, Double startLocationLat, Double startLocationLon, Double endLocationLat,
                           Double endLocationLon, Long passengerId,
                           String passengerName, String vehicleType, String vehicleNumber,
                           String rideType, Double fare, Integer rideDistance) {
}
