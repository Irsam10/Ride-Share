package com.sam.ride_service.dto;

public record RideResponse(
        Long id,
        Double startLocationLat,
        Double startLocationLon,
        Double endLocationLat,
        Double endLocationLon,
        char status,
        Long driverId,
        String driverName,
        Long passengerId,
        String passengerName,
        String vehicleType,
        String vehicleNumber,
        String rideType,
        Double fare,
        String rideStartTime,
        String rideEndTime,
        String rideDate,
        Integer rideDistance,
        Integer rideRating,
        String rideFeedback
) {}
