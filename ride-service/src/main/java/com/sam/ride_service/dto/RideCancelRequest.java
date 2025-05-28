package com.sam.ride_service.dto;

public record RideCancelRequest(Long id, String rideEndTime,
                                String rideFeedback, Integer rideRating)
{
}
