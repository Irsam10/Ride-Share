package com.sam.ride_service.service;

import com.sam.ride_service.dto.RideCancelRequest;
import com.sam.ride_service.dto.RideRequest;
import com.sam.ride_service.model.Ride;
import com.sam.ride_service.repository.RideRepository;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@Service
public class RideService {
    private final RideRepository rideRepository;
    private final GeometryFactory geometryFactory = new GeometryFactory();

    public RideService(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }
    public void createRide(RideRequest rideRequest) {

            Point startingPoint = geometryFactory.createPoint(new Coordinate(rideRequest.startLocationLat(), rideRequest.startLocationLon()));
            Point endingPoint = geometryFactory.createPoint(new Coordinate(rideRequest.endLocationLat(), rideRequest.endLocationLon()));
            // Save the ride request to the database
            Ride ride = new Ride();
            ride.setStartLocation(startingPoint);
            ride.setEndLocation(endingPoint);
            ride.setStatus("P");
            ride.setPassengerId(rideRequest.passengerId());
            ride.setPassengerName(rideRequest.passengerName());
            ride.setVehicleType(rideRequest.vehicleType());
            ride.setVehicleNumber(rideRequest.vehicleNumber());
            ride.setRideType(rideRequest.rideType());
            ride.setFare(rideRequest.fare());
            //setCurrentTIme
            ride.setRideStartTime(Time.valueOf(LocalTime.now()));
            ride.setRideDate(java.sql.Date.valueOf(LocalDate.now()));
            ride.setRideDistance(calculateDistance(startingPoint, endingPoint));
            rideRepository.save(ride);
            log.info("Ride created: {}", ride);


    }
    public void cancelRequest(RideCancelRequest rideCancelRequest)
    {
        try {
            Ride ride = rideRepository.findById(rideCancelRequest.id()).orElseThrow();
            ride.setStatus("C");
            ride.setRideEndTime(Time.valueOf(rideCancelRequest.rideEndTime()));
            ride.setRideFeedback(rideCancelRequest.rideFeedback());

            rideRepository.save(ride);
            log.info("Ride cancelled: {}", ride);
        } catch (Exception e) {
            log.error("Ride could not be cancelled: " + rideCancelRequest);
            log.error(e.getMessage());
        }
    }
    public RideRequest getRide(Long id) {
        try {
            Ride ride = rideRepository.findById(id).orElseThrow();
            Point startPoint = ride.getStartLocation();
            Point endPoint = ride.getEndLocation();
            return new RideRequest(ride.getId(), startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY(),
                    ride.getPassengerId(), ride.getPassengerName(), ride.getVehicleType(),
                    ride.getVehicleNumber(), ride.getRideType(), ride.getFare(), ride.getRideDistance());
        } catch (Exception e) {
            log.error("Ride could not be fetched: " + id);
            log.error(e.getMessage());
        }
        return null;
    }
    public List<RideRequest> getRidesByPassengerId(Long passengerId) {
        try {
            List<Ride> rides = rideRepository.findByPassengerId(passengerId);
            return rides.stream().map(ride -> {
                Point startPoint = ride.getStartLocation();
                Point endPoint = ride.getEndLocation();
                return new RideRequest(ride.getId(), startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY(),
                        ride.getPassengerId(), ride.getPassengerName(), ride.getVehicleType(),
                        ride.getVehicleNumber(), ride.getRideType(), ride.getFare(), ride.getRideDistance());

            }).toList();
        } catch (Exception e) {
            log.error("Rides could not be fetched for passenger: " + passengerId);
            log.error(e.getMessage());
        }
        return List.of();
    }
    public List<RideRequest> getRidesByDriverId(Long driverId) {
        try {
            List<Ride> rides = rideRepository.findByDriverId(driverId);
            return rides.stream().map(ride -> {
                Point startPoint = ride.getStartLocation();
                Point endPoint = ride.getEndLocation();
                return new RideRequest(ride.getId(), startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY(),
                        ride.getPassengerId(), ride.getPassengerName(), ride.getVehicleType(),
                        ride.getVehicleNumber(), ride.getRideType(), ride.getFare(), ride.getRideDistance());

            }).toList();
        } catch (Exception e) {
            log.error("Rides could not be fetched for driver: " + driverId);
            log.error(e.getMessage());
        }
        return List.of();
    }
    public List<RideRequest> getRidesByStatus(String status)
    {
        List<Ride> rides = rideRepository.findByStatus(status);
        return rides.stream().map(ride -> {
            Point startPoint = ride.getStartLocation();
            Point endPoint = ride.getEndLocation();
            return new RideRequest(ride.getId(), startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY(),
                    ride.getPassengerId(), ride.getPassengerName(), ride.getVehicleType(),
                    ride.getVehicleNumber(), ride.getRideType(), ride.getFare(), ride.getRideDistance());

        }).toList();
    }
    public void updateRidesStatus(List<Long> rideIds, String status) {
        try {
            List<Ride> rides = rideRepository.findAllById(rideIds);
            for (Ride ride : rides) {
                ride.setStatus(status);
            }
            rideRepository.saveAll(rides);
            log.info("Ride statuses updated to: {}", status);
        } catch (Exception e) {
            log.error("Ride statuses could not be updated: " + rideIds);
            log.error(e.getMessage());
        }
    }
    public Integer calculateDistance(Point start, Point end) {
        double lat1 = start.getY();
        double lon1 = start.getX();
        double lat2 = end.getY();
        double lon2 = end.getX();

        final int R = 6371; // Radius of Earth in kilometers

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distanceInKm = R * c;

        return (int) (distanceInKm * 1000); // Return distance in meters
    }


    public List<RideRequest> transitionRidesStatus(String fromStatus, String toStatus) {
        try {
            List<Ride> rides = rideRepository.findByStatus(fromStatus);
            for (Ride ride : rides) {
                ride.setStatus(toStatus);
            }
            rideRepository.saveAll(rides);
            log.info("Rides transitioned from {} to {}", fromStatus, toStatus);
            return rides.stream().map(ride -> {
                Point startPoint = ride.getStartLocation();
                Point endPoint = ride.getEndLocation();
                return new RideRequest(ride.getId(), startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY(),
                        ride.getPassengerId(), ride.getPassengerName(), ride.getVehicleType(),
                        ride.getVehicleNumber(), ride.getRideType(), ride.getFare(), ride.getRideDistance());

            }).toList();
        } catch (Exception e) {
            log.error("Rides could not be transitioned from {} to {}", fromStatus, toStatus);
            log.error(e.getMessage());
        }
        return List.of();
    }
}
