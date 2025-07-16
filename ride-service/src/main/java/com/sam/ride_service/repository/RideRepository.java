package com.sam.ride_service.repository;

import com.sam.ride_service.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RideRepository extends JpaRepository<Ride, Long> {
    // Custom query methods can be defined here if needed
    // For example, find rides by user ID, status, etc.
    List<Ride> findByStatus(String status);

    List<Ride> findByPassengerId(Long passengerId);

    List<Ride> findByDriverId(Long driverId);
}
