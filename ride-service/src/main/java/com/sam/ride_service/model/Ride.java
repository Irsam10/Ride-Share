package com.sam.ride_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "t_ride")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Point startLocation;
    private Point endLocation;
    private char status; // 'O' for ongoing, 'C' for completed, 'F' for failed, 'R' for rejected, 'A' for accepted,
    // 'S' for scheduled, 'X' for cancelled, 'P' for pending
    private Long driverId;
    private String driverName;
    private Long passengerId;
    private String passengerName;
    private String vehicleType;
    private String vehicleNumber;
    private String rideType;
    private Double fare;
    private Time rideStartTime;
    private Time rideEndTime;
    private Date rideDate;
    private Integer rideDistance;
    private Integer rideRating;
    private String rideFeedback;

}
